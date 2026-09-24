package main.java.com.jgunzalesindustries.banco.sahur.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import main.java.com.jgunzalesindustries.banco.sahur.model.Loan;
import main.java.com.jgunzalesindustries.banco.sahur.repository.LoanRepository;

public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService() {
        this.loanRepository = new LoanRepository();
    }

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }


    public ObservableList<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(String idLoan) {
        return findByIdOrThrow(idLoan);
    }

    /**
     * Devuelve únicamente los préstamos de un cliente puntual. Se usa para
     * que un usuario con rol Cliente solo pueda ver SUS propios préstamos.
     */
    public ObservableList<Loan> getLoansByClientId(String idClient) {
        ObservableList<Loan> propios = FXCollections.observableArrayList();
        if (idClient == null || idClient.isBlank()) {
            return propios;
        }
        for (Loan loan : loanRepository.findAll()) {
            if (idClient.equals(loan.getIdClient())) {
                propios.add(loan);
            }
        }
        return propios;
    }


    public boolean applyForLoan(Loan loan) {
        validateLoan(loan);

        loan.setStatus("Pendiente");
        loan.setRequestDate(LocalDate.now());
        loan.setApprovalDate(null);

        return loanRepository.save(loan);
    }


    public boolean updateLoan(Loan loan) {
        if (loan.getIdLoan() == null || loan.getIdLoan().isBlank()) {
            throw new IllegalArgumentException("El ID del préstamo es obligatorio.");
        }
        if (!loanRepository.existsById(loan.getIdLoan())) {
            throw new IllegalArgumentException("No se encontró el préstamo con ID: " + loan.getIdLoan());
        }
        validateLoan(loan);

        return loanRepository.update(loan);
    }


    public boolean approveLoan(String idLoan) {
        Loan loan = findByIdOrThrow(idLoan);
        validateNotFinalState(loan);

        loan.setStatus("Aprobado");
        loan.setApprovalDate(LocalDate.now());

        return loanRepository.update(loan);
    }

    public boolean rejectLoan(String idLoan) {
        Loan loan = findByIdOrThrow(idLoan);
        validateNotFinalState(loan);

        loan.setStatus("Rechazado");
        loan.setApprovalDate(null);

        return loanRepository.update(loan);
    }


    public boolean deleteLoan(String idLoan) {
        if (idLoan == null || idLoan.isBlank()) {
            throw new IllegalArgumentException("El ID del préstamo es obligatorio.");
        }
        if (!loanRepository.existsById(idLoan)) {
            throw new IllegalArgumentException("No se encontró el préstamo con ID: " + idLoan);
        }
        return loanRepository.deleteById(idLoan);
    }

    // ---------- Helpers privados ----------

    private void validateLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("El préstamo no puede ser nulo.");
        }
        if (loan.getIdClient() == null || loan.getIdClient().isBlank()) {
            throw new IllegalArgumentException("El ID del cliente es obligatorio.");
        }
        if (loan.getAmount() <= 0) {
            throw new IllegalArgumentException("El monto solicitado debe ser mayor a 0.");
        }
        if (loan.getThermMonths() <= 0) {
            throw new IllegalArgumentException("El plazo en meses debe ser mayor a 0.");
        }
        if (loan.getInterestRate() < 0) {
            throw new IllegalArgumentException("La tasa de interés no puede ser negativa.");
        }
    }

    private void validateNotFinalState(Loan loan) {
        String status = loan.getStatus();
        if ("aprobado".equalsIgnoreCase(status) || "rechazado".equalsIgnoreCase(status)) {
            throw new IllegalStateException("El préstamo ya se encuentra en estado definitivo: " + status);
        }
    }

    private Loan findByIdOrThrow(String idLoan) {
        return loanRepository.findAll().stream()
                .filter(l -> l.getIdLoan().equals(idLoan))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el préstamo con ID: " + idLoan));
    }
}
