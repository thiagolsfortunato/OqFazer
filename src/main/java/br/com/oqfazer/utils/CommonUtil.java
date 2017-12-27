package br.com.oqfazer.utils;

import java.util.Collection;

/**
 * A classe CommonUtil e responsavel por verificar se um objeto esta Nulo ou Vazio
 * @author Thiago Fortunato
 * @version 1.0
 */
public class CommonUtil {
	
	public static boolean isNotNullOrNotEmpty(Object object) {
		return !isNullOrEmpty(object);
	}
	
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object object) {

        boolean nullOrEmpty = false;

        if (object == null) {
            nullOrEmpty = true;

        } else {

            if (object instanceof Collection) {

                if (((Collection) object).size() == 0) {
                    nullOrEmpty = true;
                }

            } else if (object instanceof String) {

                if (((String) object).length() == 0) {
                    nullOrEmpty = true;
                }
            }
        }
        return nullOrEmpty;
    }

    public static String toString(Object str) {

        if (str != null) {
            return String.valueOf(str).trim();
        }
        return "";
    }
}
