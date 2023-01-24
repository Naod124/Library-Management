package Model;

import jakarta.persistence.*;


@NamedQueries({
        @NamedQuery(

                name = Employees.updateSalary, query = "update Employees e set e.Salary = :salary where e.id = :id")
})


@Table(name = "Employees")
@Entity
public class Employees {

    public static final String updateSalary = "Employees.updateSalary";


    @Id
    @Column(name = "id", nullable = false, precision = 0)
    private int id;

    @Column(name = "FirstName", precision = 0)
    private String name;

    @Column(name = "LastName", precision = 0)
    private String LastName;

    @Column(name = "Salary", precision = 0)
    private String Salary;

    @Column(name = "isCEO", precision = 0)
    private Boolean isCeo;

    @Column(name = "isManager", nullable = false, precision = 0)
    private Boolean isManager;

    @Column(name = "ManagerId", nullable = false, precision = 0)
    private int managerId;

    private int rank;

    public Employees() {
    }

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

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public Boolean getCeo() {
        return isCeo;
    }

    public void setCeo(Boolean ceo) {
        isCeo = ceo;
    }

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
