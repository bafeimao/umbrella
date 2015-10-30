package net.bafeimao.umbrella.support.data;

import com.google.api.client.util.Objects;
import net.bafeimao.umbrella.support.util.Numbers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by bafeimao on 2015/10/28.
 */
public class Criteria {
    static final Logger LOGGER = LoggerFactory.getLogger(Criteria.class);

    private final String property;
    private final FilterOperator operator;
    private final Object value;
    private boolean or;

    public Criteria(String property, FilterOperator operator, Object value) {
        this(property, operator, value, false);
    }

    public Criteria(String property, FilterOperator operator, Object value, boolean or) {
        this.property = property;
        this.operator = operator;
        this.value = value;
        this.or = or;
    }

    public String getProperty() {
        return property;
    }

    public FilterOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public boolean isOr() {
        return or;
    }

    public void setOr(boolean or) {
        this.or = or;
    }

    public boolean checkValid(Object entity) throws NoSuchFieldException, IllegalAccessException {
        String fieldName = this.getProperty();
        FilterOperator operator = this.getOperator();
        Object value = this.getValue();
        Class<?> fieldValueClass;
        Object fieldValue;

        Field field = entity.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            fieldValue = field.get(entity);
            fieldValueClass = fieldValue.getClass();
            field.setAccessible(false);

            if (isOperatorSupported(operator, fieldValueClass)) {
                switch (operator) {
                    case EQ:
                        return Objects.equal(value, fieldValue);
                    case NEQ:
                        return !Objects.equal(value, fieldValue);
                    case GT:
                    case GTE:
                    case LT:
                    case LTE:
                        int result = Numbers.compare(fieldValue, value);

                        if (operator == FilterOperator.GT) {
                            return result > 0;
                        } else if (operator == FilterOperator.GTE) {
                            return result >= 0;
                        } else if (operator == FilterOperator.GT) {
                            return result < 0;
                        } else if (operator == FilterOperator.GTE) {
                            return result <= 0;
                        }

                        break;
                    case CONTAINS:
                        if (fieldValueClass.equals(String.class)) {
                            return ((String) fieldValue).contains((String) value);
                        }
                    case STARTS_WITH:
                        if (fieldValueClass.equals(String.class)) {
                            return ((String) fieldValue).startsWith((String) value);
                        }
                    case ENDS_WITH:
                        if (fieldValueClass.equals(String.class)) {
                            return ((String) fieldValue).endsWith((String) value);
                        }
                }
            }
        }

        return true;
    }

    private boolean isOperatorSupported(FilterOperator op, Class<?> clazz) {
        boolean retVal = false;

        if (op == FilterOperator.EQ || op == FilterOperator.NEQ) {
            return true;
        }

        // 以下操作只能应用在String类型上
        if (op == FilterOperator.CONTAINS || op == FilterOperator.STARTS_WITH || op == FilterOperator.ENDS_WITH) {
            return clazz.equals(String.class);
        }

        // 以下操作只能应用在数值类型中
        if (op == FilterOperator.GT || op == FilterOperator.GTE || op == FilterOperator.LT || op == FilterOperator.LTE) {
            return clazz.equals(Double.class) || clazz.equals(double.class)
                    || clazz.equals(Float.class) || clazz.equals(float.class)
                    || clazz.equals(Long.class) || clazz.equals(long.class)
                    || clazz.equals(Integer.class) || clazz.equals(int.class)
                    || clazz.equals(Short.class) || clazz.equals(short.class)
                    || clazz.equals(Byte.class) || clazz.equals(byte.class);
        }

        if (retVal == false) {
            LOGGER.info("The operator:{} cannot apply to {} type", op, clazz.getSimpleName());
        }

        return retVal;
    }
}
