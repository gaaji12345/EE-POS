package dto;


import entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private double salary;

    public Customer toEntity(){
        Customer cus = new Customer();
        cus.setId(this.id);
        cus.setName(this.name);
        cus.setAddress(this.address);
        cus.setSalary(this.salary);
        return cus;
    }
}
