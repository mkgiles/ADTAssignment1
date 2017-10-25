/**
 * 
 */
package model;

/**
 * The Interface for model entities which need to be converted to CSV.
 *
 * @author Conor James Giles
 */
public interface CSV {

	/**
	 * Convert model to CSV.
	 *
	 * @return the string
	 */
	String toCSV();
}
