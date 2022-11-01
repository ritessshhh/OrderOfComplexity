package OrderOfComplexityCalculator;
/**
 * Ritesh Sunil Chavan
 */

/**
 * This class prints the order of complexities and stores the power for n and log(n)
 */
public class Complexity {
    /**
     * This variable stores the power of n
     */
    private int n_power;
    /**
     * This variable stores the power value of log(n)
     */
    private int log_power;


    /**
     * Getter methods for n_power
     * @return power of n
     */
    public int getN_power() {
        return n_power;
    }

    /**
     * Setter method for n_power
     * @param n_power power of n
     */
    public void setN_power(int n_power) {
        this.n_power = n_power;
    }

    /**
     * Getter method for log_power
     * @return power of log(n)
     */
    public int getLog_power() {
        return log_power;
    }

    /**
     * Setter method for log_power
     * @param log_power power of log
     */
    public void setLog_power(int log_power) {
        this.log_power = log_power;
    }

    /**
     * Prints order of complexity
     * @return order of complexity
     */
    public String toString() {
        String result="O(1)";
        String nresult="";
        String logresult="";
        if (n_power==1)
            nresult="n";
        else if (n_power>1) {
            nresult="n^"+n_power;
        }

        if (log_power==1)
            logresult="log(n)";
        else if (log_power>1)
            logresult="log(n)^"+log_power;

        if (!nresult.isBlank()&&!logresult.isBlank())
        {
            result="O("+nresult+" * "+logresult+")";
        } else if (!nresult.isBlank()) {
            result="O("+nresult+")";
        }
        else if (!logresult.isBlank()) {
            result="O("+logresult+")";
        }

        return result;
    }
}