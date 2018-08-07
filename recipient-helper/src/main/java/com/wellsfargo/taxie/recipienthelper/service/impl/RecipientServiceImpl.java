package com.wellsfargo.taxie.recipienthelper.service.impl;

import com.wellsfargo.taxie.recipienthelper.service.RecipientService;
import com.wellsfargo.taxie.recipienthelper.domain.Recipient;
import com.wellsfargo.taxie.recipienthelper.repository.RecipientRepository;
import com.wellsfargo.taxie.recipienthelper.service.dto.RecipientDTO;
import com.wellsfargo.taxie.recipienthelper.service.mapper.RecipientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Recipient.
 */
@Service
@Transactional
public class RecipientServiceImpl implements RecipientService {

    private final Logger log = LoggerFactory.getLogger(RecipientServiceImpl.class);

    private final RecipientRepository recipientRepository;

    private final RecipientMapper recipientMapper;

    public RecipientServiceImpl(RecipientRepository recipientRepository, RecipientMapper recipientMapper) {
        this.recipientRepository = recipientRepository;
        this.recipientMapper = recipientMapper;
    }

    /**
     * Save a recipient.
     *
     * @param recipientDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecipientDTO save(RecipientDTO recipientDTO) {
        log.debug("Request to save Recipient : {}", recipientDTO);
        Recipient recipient = recipientMapper.toEntity(recipientDTO);
        recipient = recipientRepository.save(recipient);
        return recipientMapper.toDto(recipient);
    }

    /**
     * Get all the recipients.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RecipientDTO> findAll() {
        log.debug("Request to get all Recipients");
        return recipientRepository.findAll().stream()
            .map(recipientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one recipient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecipientDTO> findOne(Long id) {
        log.debug("Request to get Recipient : {}", id);
        return recipientRepository.findById(id)
            .map(recipientMapper::toDto);
    }

    /**
     * Delete the recipient by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recipient : {}", id);
        recipientRepository.deleteById(id);
    }
}
