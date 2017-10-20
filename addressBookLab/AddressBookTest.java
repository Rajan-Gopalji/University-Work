
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * The test class AddressBookTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class AddressBookTest
{
    AddressBook book;

    /**
     * Default constructor for test class AddressBookTest
     */
    public AddressBookTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        book = new AddressBook();
        book.addAddress(new Address("John Smith", "1 High Street", "0121 987 4321", "js@example.com"));
        book.addAddress(new Address("Helen Taylor", "The old house", "0121 444 555", "helen@example.com"));
        book.addAddress(new Address("Mary Smith", "Aston Triangle", "0121 999 888", "marysmith@example.com"));
        book.addAddress(new Address("Mohammed Ali", "3 Kings Road", "020 789 1234", "mohammed@example.com"));
        book.addAddress(new Address("Chan Li", "1 Queens road", "0121 333 3333", "chan@example.com"));
        book.addAddress(new Address("Tom Brown", "The Cottage", "0121 222 7654", "tb@example.com"));
        book.addAddress(new Address("Mary Brown", "The Cottage", "0121 222 7654", "mary@example.com"));
        book.addAddress(new Address("Peter Cook", "Some Street", "0121 222 3333", "peter@example.com"));
        book.addAddress(new Address("Cathy Smith", "100 Bristol Road", "0121 123 4567", "cathy@example.com"));
        book.addAddress(new Address("Mike Taylor", "101 Bristol Road", "0121 234 5678", "mike@example.com"));
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }


}
