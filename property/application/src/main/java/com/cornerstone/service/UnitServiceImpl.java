package com.cornerstone.service;

import com.cornerstone.dto.UnitDto;
import com.cornerstone.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    // AGREGADO: extrae el número al inicio del unitNumber para ordenar numéricamente
    // Ejemplos: "9" -> 9, "100" -> 100, "140-1" -> 140, "Shed" -> Integer.MAX_VALUE (va al final)
    private int extractLeadingNumber(String unitNumber) {

        if (unitNumber == null || unitNumber.isBlank()) {
            return Integer.MAX_VALUE;
        }

        String number =
                unitNumber.replaceAll("[^0-9].*", "");

        if (number.isBlank()) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }

    }

    @Override
    public List<UnitDto> getAll() {
        return unitRepository.getAll()
                .stream()
                // CAMBIADO: ahora ordena numéricamente en vez de alfabéticamente
                // primero por número principal, luego alfabético para desempatar (ej: 140-1, 140-2)
                .sorted(Comparator.comparingInt(
                                (UnitDto u) -> extractLeadingNumber(u.getUnitNumber()))
                        .thenComparing(UnitDto::getUnitNumber,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    @Override
    public Optional<UnitDto> get(Long id) {
        return unitRepository.get(id);
    }

    @Override
    public UnitDto create(UnitDto unit) {
        return unitRepository.save(unit);
    }

    @Override
    public UnitDto update(Long id, UnitDto unit) {
        unit.setId(id);
        return unitRepository.save(unit);
    }

    @Override
    public void delete(Long id) {
        unitRepository.delete(id);
    }

    @Override
    public Optional<UnitDto> getByUnitNumber(String unitNumber) {
        return unitRepository.getByUnitNumber(unitNumber);
    }
}