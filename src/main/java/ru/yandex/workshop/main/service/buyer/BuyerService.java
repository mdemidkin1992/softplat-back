package ru.yandex.workshop.main.service.buyer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.workshop.main.exception.DuplicateException;
import ru.yandex.workshop.main.exception.EntityNotFoundException;
import ru.yandex.workshop.main.message.ExceptionMessage;
import ru.yandex.workshop.main.model.buyer.Buyer;
import ru.yandex.workshop.main.repository.buyer.BuyerRepository;
import ru.yandex.workshop.security.service.UserDetailsChangeService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final UserDetailsChangeService userDetailsChangeService;

    public void addBuyer(Buyer buyer) {
        if (checkIfUserExistsByEmail(buyer.getEmail()))
            throw new DuplicateException(ExceptionMessage.DUPLICATE_EXCEPTION.label + buyer.getEmail());

        buyer.setRegistrationTime(LocalDateTime.now());
        buyerRepository.save(buyer);
    }

    public Buyer updateBuyer(String email, Buyer updateRequest) {
        Buyer oldBuyer = getSecurityBuyer(email);

        if (updateRequest.getName() != null) {
            oldBuyer.setName(updateRequest.getName());
        }
        if (updateRequest.getEmail() != null) {
            if (checkIfUserExistsByEmail(updateRequest.getEmail()))
                throw new DuplicateException(ExceptionMessage.DUPLICATE_EXCEPTION.label + updateRequest.getEmail());
            userDetailsChangeService.changeEmail(oldBuyer.getEmail(), updateRequest.getEmail());
            oldBuyer.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getPhone() != null) {
            oldBuyer.setPhone(updateRequest.getPhone());
        }

        return buyerRepository.save(oldBuyer);
    }

    @Transactional(readOnly = true)
    public Buyer getBuyer(Long userId) {
        return buyerRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessage.ENTITY_NOT_FOUND_EXCEPTION.label)
        );
    }

    @Transactional(readOnly = true)
    public Buyer getSecurityBuyer(String email) {
        return buyerRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.ENTITY_NOT_FOUND_EXCEPTION.label));
    }

    @Transactional(readOnly = true)
    public boolean checkIfUserExistsByEmail(String email) {
        return buyerRepository.findByEmail(email).isPresent();
    }
}
