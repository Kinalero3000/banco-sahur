package main.java.com.jgunzalesindustries.banco.sahur.repository;
 
 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import main.java.com.jgunzalesindustries.banco.sahur.config.DataBaseConnection;
import main.java.com.jgunzalesindustries.banco.sahur.model.Loan;
 
public class LoanRepository {
 
    public ObservableList<Loan> findAll() {
        String sql = "SELECT * FROM loans;";
        ObservableList<Loan> list = FXCollections.observableArrayList();
 
        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
 
            while (rs.next()) {
                LocalDate requestDate = rs.getDate("request_date") != null ? rs.getDate("request_date").toLocalDate() : null;
                LocalDate approvalDate = rs.getDate("approval_date") != null ? rs.getDate("approval_date").toLocalDate() : null;
 
                list.add(new Loan(
                        rs.getString("id_prestamo"),
                        rs.getString("id_cliente"),
                        rs.getInt("id_tipo_credito"),
                        rs.getDouble("monto"),
                        rs.getInt("tasa_interes"),
                        rs.getInt("plazo_meses"),
                        requestDate,
                        approvalDate,
                        rs.getString("estado")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving loans list.", e);
        }
    }
 
    public boolean deleteById(String idLoan) {
        String sql = "DELETE FROM prestamo WHERE id_prestamo= ?;";
 
        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            pstm.setString(1, idLoan);
            int affectedRows = pstm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting loan from database.", e);
        }
    }
 
    public boolean save(Loan loan) {
        String sql = "INSERT INTO prestamo (id_prestamo, id_cliente, id_tipo_credito, , monto, tasa_interes, plazo_meses, fecha_solicitud, fecha_aprobacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
 
        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            pstm.setString(1, loan.getIdLoan());
            pstm.setString(2, loan.getIdClient());
            pstm.setInt(3, loan.getidTypeCredit());
            pstm.setDouble(4, loan.getAmount());
            pstm.setInt(5, loan.getInterestRate());
            pstm.setInt(6, loan.getThermMonths());
            pstm.setDate(7, loan.getRequestDate() != null ? java.sql.Date.valueOf(loan.getRequestDate()) : null);
            pstm.setDate(8, loan.getApprovalDate() != null ? java.sql.Date.valueOf(loan.getApprovalDate()) : null);
            pstm.setString(9, loan.getStatus());
 
            int affectedRows = pstm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving loan to database.", e);
        }
    }
 
    public boolean update(Loan loan) {
        String sql = "UPDATE loans SET id_client = ?, id_fee = ?, pay_day = ?, amount = ?, interest_rate = ?, term_months = ?, request_date = ?, approval_date = ?, status = ? WHERE id_loan = ?;";
 
        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            pstm.setString(1, loan.getIdClient());
            pstm.setString(2, loan.getIdClient());
            pstm.setInt(3, loan.getidTypeCredit());
            pstm.setDouble(4, loan.getAmount());
            pstm.setInt(5, loan.getInterestRate());
            pstm.setInt(6, loan.getThermMonths());
            pstm.setDate(7, loan.getRequestDate() != null ? java.sql.Date.valueOf(loan.getRequestDate()) : null);
            pstm.setDate(8, loan.getApprovalDate() != null ? java.sql.Date.valueOf(loan.getApprovalDate()) : null);
            pstm.setString(9, loan.getStatus());
            pstm.setString(10, loan.getIdLoan());
 
            int affectedRows = pstm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating loan in database.", e);
        }
    }
 
    public boolean existsById(String idLoan) {
        String sql = "SELECT id_loan FROM loans WHERE id_loan = ?;";
 
        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            pstm.setString(1, idLoan);
            ResultSet rs = pstm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Error validating loan existence.", e);
        }
    }
}
