package pl.garciapl.currencyfair.controller.validator;

import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.garciapl.currencyfair.controller.json.Request;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lukasz on 17.05.15.
 */
public class RequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Request request = (Request) target;

        ValidationUtils.rejectIfEmpty(errors, "userId", "Empty userId field");
        ValidationUtils.rejectIfEmpty(errors, "currencyFrom", "Empty currencyFrom field");
        ValidationUtils.rejectIfEmpty(errors, "currencyTo", "Empty currencyTo field");
        ValidationUtils.rejectIfEmpty(errors, "amountSell", "Empty amountSell field");
        ValidationUtils.rejectIfEmpty(errors, "amountBuy", "Empty amountBuy field");
        ValidationUtils.rejectIfEmpty(errors, "rate", "Empty rate field");
        ValidationUtils.rejectIfEmpty(errors, "originatingCountry", "Empty originatingCountry field");

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy hh:mm:ss", Locale.ENGLISH);
            if (request.getTimePlaced() != null) {
                simpleDateFormat.setDateFormatSymbols(new DateFormatSymbols(Locale.ENGLISH));
                simpleDateFormat.parse(request.getTimePlaced().trim());
            } else {
                String format = simpleDateFormat.format(new Date());
                request.setTimePlaced(format);
            }
        } catch (IllegalArgumentException | ParseException e) {
            errors.rejectValue("timePlaced", e.getLocalizedMessage());
        }

        try {
            CurrencyUnit.of(request.getCurrencyFrom());
        } catch (IllegalCurrencyException e) {
            errors.rejectValue("currencyFrom", e.getLocalizedMessage());
        }

        try {
            CurrencyUnit.of(request.getCurrencyTo());
        } catch (IllegalCurrencyException e) {
            errors.rejectValue("currencyTo", e.getLocalizedMessage());
        }

        try {
            if (CurrencyUnit.of(request.getCurrencyFrom()).equals(CurrencyUnit.of(request.getCurrencyTo()))) {
                errors.rejectValue("currencyFrom", "Comparable value to currencyTo");
            }
        } catch (IllegalCurrencyException ignored) {
        }

        if (request.getAmountSell() != null) {
            if (request.getAmountSell().compareTo(BigDecimal.ZERO) == 0 || request.getAmountSell().compareTo(BigDecimal.ZERO) < 0) {
                errors.rejectValue("amountSell", "Invalid value");
            }
        }

        if (request.getAmountBuy() != null) {
            if (request.getAmountBuy().compareTo(BigDecimal.ZERO) == 0 || request.getAmountBuy().compareTo(BigDecimal.ZERO) < 0) {
                errors.rejectValue("amountBuy", "Invalid value");
            }
        }

        if (request.getAmountSell() != null && request.getAmountBuy() != null) {
            if (request.getAmountSell().compareTo(request.getAmountBuy()) == 0) {
                errors.rejectValue("amountSell", "Comparable to amountBuy");
                errors.rejectValue("amountBuy", "Comparable to amountSell");
            }
        }

        if (request.getRate() != null) {
            if (request.getRate().compareTo((double) 0) == 0 || request.getRate().compareTo((double) 0) < 0) {
                errors.rejectValue("rate", "Invalid value");
            }
        }

        boolean originCountryAvailable = false;
        for (String countryCode : Locale.getISOCountries()) {
            if (countryCode.equals(request.getOriginatingCountry())) {
                originCountryAvailable = true;
                break;
            }
        }
        if (!originCountryAvailable) {
            errors.rejectValue("originatingCountry", "Invalid value");
        }

        try {
            if (request.getAmountSell() != null && request.getAmountBuy() != null & request.getRate() != null) {
                BigDecimal multiply = processMultiplyTransaction(request.getAmountSell(), request.getAmountBuy(), calculateRate(request.getRate()), getMathContext(request.getRate()));
                if (!compareValues(multiply, request.getAmountBuy())) {
                    errors.rejectValue("amountBuy", "Invalid value compared to multiplied amountSell and rate. AmountBuy expected " + multiply.toPlainString());
                }
            }
        } catch (Exception e) {
            errors.rejectValue("amountSell", "Exception " + e.getMessage());
            errors.rejectValue("amountBuy", "Exception " + e.getMessage());
        }
    }

    private BigDecimal calculateRate(Double rate) {
        BigDecimal decimal = new BigDecimal(rate, MathContext.DECIMAL64);
        return decimal.setScale(countFraction(rate), RoundingMode.CEILING);
    }

    private MathContext getMathContext(Double rate) {
        return new MathContext(countFraction(rate), RoundingMode.HALF_UP);
    }

    private BigDecimal processMultiplyTransaction(BigDecimal first, BigDecimal second, BigDecimal rate, MathContext mathContext) {
        BigDecimal multiply = first.multiply(rate, mathContext);
        multiply = multiply.setScale(second.scale(), RoundingMode.CEILING);
        return multiply;
    }

    private BigDecimal processDivideTransaction(BigDecimal first, BigDecimal second, BigDecimal rate, MathContext mathContext) {
        BigDecimal divide = first.divide(rate, mathContext);
        divide = divide.setScale(second.scale(), RoundingMode.CEILING);
        return divide;
    }

    private boolean compareValues(BigDecimal multiply, BigDecimal value) {
        return multiply.compareTo(value) == 0;
    }

    private int countFraction(double input) {
        return new BigDecimal(Double.toString(input)).stripTrailingZeros().scale();
    }
}
