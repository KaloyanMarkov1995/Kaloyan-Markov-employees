import java.util.Date;

public class Employee {
    private int empId;
    private int projectId;
    private Date dateFrom;
    private Date dateTo;

    public Employee(int empId, int projectId, Date dateFrom, Date dateTo) {
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmpId() {
        return empId;
    }

    public int getProjectId() {
        return projectId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

}
