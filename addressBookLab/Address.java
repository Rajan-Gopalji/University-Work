
/**
 * Class to model contact details with a name, address, phone number and
 * email address.
 * 
 * @author AJB
 * @version 1.0
 */
public class Address
{
  // instance variables - replace the example below with your own
  private String name;
  private String address;
  private String phone;
  private String email;
  
  /**
   * Constructor for objects of class Address
   */
  public Address(String name, String address, String phone, String email)
  {
    // initialise instance variables
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.email = email;
  }

  /**
   * Get the email address.
   * @return The email address for this contact.
   */
  public String getEmail() {
    return email;
  }
  
  /**
   * Get the phone number.
   * @return The phone number for this person.
   */
  public String getPhone() {
    return phone;
  }
  
  /**
   * Set a new phone number.
   * @param newPhone  The new phone number for this person.
   */
  public void setPhone(String  newPhone) {
    this.phone = newPhone;
  }
  
  /**
   * Get the address of this person.
   * @return The address for this person.
   */
  public String getAddress() {
    return address;
  }
  
  /**
   * Get the name of this contact.
   * @return The name of this person.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Generate a String describing this address.
   * @return a String giving the state of this address in the
   * following format: "AJB, Aston University, phone: 0121 2043000
   * email: a.j.beaumont@aston.ac.uk" where AJB is the name, Aston
   * University is the address etc.
   */
  public String toString() {
    return name + ", " + address + ", phone: " + 
      phone + ", email: " + email;
  }

}
