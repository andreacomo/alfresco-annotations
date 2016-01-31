package it.cosenonjaviste.alfresco.annotations.constants;


/**
 * Transactions types.
 *
 * More details on <a href="https://wiki.alfresco.com/wiki/Web_Scripts#Creating_a_Description_Document">Anfresco Wiki</a>
 *
 * @author Andrea Como
 */
public enum TransactionType {

    /**
     * Specifies that no transaction is required at all.
     *
     * <p>
     *     It is default value if {@link AuthenticationType#NONE}
     * </p>
     */
    NONE,

    /**
     * Specifies that a transaction is required (and will inherit an existing transaction, if open)
     *
     * <p>
     *     It is default value if {@link AuthenticationType} IS NOT {@link AuthenticationType#NONE}
     * </p>
     */
    REQUIRED,

    /**
     * Specifies that a new transaction is required
     */
    REQUIRES_NEW {
        @Override
        public String toString() {
            return "requiresnew";
        }
    },

    /**
     * Do not specify transaction type so:
     *
     * <ul>
     *     <li>it's {@link #NONE} if {@link AuthenticationType#NONE}</li>
     *     <li>else {@link #REQUIRED}</li>
     * </ul>
     *
     */
    DEFAULT {
        @Override
        public String toString() {
            return "";
        }
    };

    public String toString() {
        return this.name().toLowerCase();
    }
}
