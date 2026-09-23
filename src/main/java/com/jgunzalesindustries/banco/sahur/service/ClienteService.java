package main.java.com.jgunzalesindustries.banco.sahur.service;

import main.java.com.jgunzalesindustries.banco.sahur.dto.request.ClienteDTORequest;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.ClienteDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.model.Clients;
import main.java.com.jgunzalesindustries.banco.sahur.repository.ClienteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClienteService {

    private static final Pattern DPI_PATTERN = Pattern.compile("^\\d{13}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    public ClienteDTOResponse registerCliente(ClienteDTORequest request) {
        validate(request);

        if (clienteRepository.findByDpi(request.getDpi()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente registrado con ese DPI.");
        }

        Clients cliente = new Clients(
                UUID.randomUUID().toString(),
                request.getDpi(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhone(),
                request.getEmail(),
                request.getAddress(),
                LocalDate.now()
        );

        clienteRepository.save(cliente);
        return toResponse(cliente);
    }

    public ClienteDTOResponse updateCliente(String id, ClienteDTORequest request) {
        validate(request);

        Clients existing = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        existing.setDPI(request.getDpi());
        existing.setName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setPhone(request.getPhone());
        existing.setEmail(request.getEmail());
        existing.setAddress(request.getAddress());

        clienteRepository.update(existing);
        return toResponse(existing);
    }

    public void deleteCliente(String id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));
        clienteRepository.delete(id);
    }

    public List<ClienteDTOResponse> listClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private void validate(ClienteDTORequest request) {
        if (request.getDpi() == null || !DPI_PATTERN.matcher(request.getDpi()).matches()) {
            throw new IllegalArgumentException("El DPI debe tener 13 dígitos numéricos.");
        }
        if (request.getFirstName() == null || request.getFirstName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (request.getLastName() == null || request.getLastName().isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && !EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            throw new IllegalArgumentException("El correo no tiene un formato válido.");
        }
    }

    private ClienteDTOResponse toResponse(Clients cliente) {
        return new ClienteDTOResponse(
                cliente.getIdCliente(),
                cliente.getDPI(),
                cliente.getName(),
                cliente.getLastName(),
                cliente.getPhone(),
                cliente.getEmail(),
                cliente.getAddress(),
                cliente.getRegisterDate()
        );
    }
}