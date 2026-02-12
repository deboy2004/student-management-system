import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract class representing a Person in the Student Information System.
 * Implements Serializable for file-based storage.
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Common attributes for all person types
    protected int id;
    protected String name;
    protected String address;
    protected String contactNumber;
    protected Date dateOfBirth;
    
    // Constructor
    public Person(int id, String name, String address, String contactNumber, Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.dateOfBirth = dateOfBirth;
    }
    
    // Default constructor
    public Person() {
        this.id = 0;
        this.name = "";
        this.address = "";
        this.contactNumber = "";
        this.dateOfBirth = new Date();
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    // Format date for display
    public String getFormattedDateOfBirth() {
        if (dateOfBirth == null) return "Not set";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dateOfBirth);
    }
    
    // Abstract method to display person details
    public abstract String getDetails();
    
    @Override
    public String toString() {
        return "ID: " + id + 
               "\nName: " + name + 
               "\nAddress: " + address + 
               "\nContact: " + contactNumber + 
               "\nDate of Birth: " + getFormattedDateOfBirth();
    }
}
