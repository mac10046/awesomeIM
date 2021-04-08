package com.sls.awesomeim.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sls.awesomeim.domain.Customers;
import com.sls.awesomeim.repository.CustomersRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.Customers}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomersResource {

    private final Logger log = LoggerFactory.getLogger(CustomersResource.class);

    private static final String ENTITY_NAME = "customers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersRepository customersRepository;

    public CustomersResource(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    /**
     * {@code POST  /customers} : Create a new customers.
     *
     * @param customers the customers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customers, or with status {@code 400 (Bad Request)} if the customers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers")
    public ResponseEntity<Customers> createCustomers(@Valid @RequestBody Customers customers) throws URISyntaxException {
        log.debug("REST request to save Customers : {}", customers);
        if (customers.getId() != null) {
            throw new BadRequestAlertException("A new customers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Customers result = customersRepository.save(customers);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers} : Updates an existing customers.
     *
     * @param customers the customers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customers,
     * or with status {@code 400 (Bad Request)} if the customers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers")
    public ResponseEntity<Customers> updateCustomers(@Valid @RequestBody Customers customers) throws URISyntaxException {
        log.debug("REST request to update Customers : {}", customers);
        if (customers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Customers result = customersRepository.save(customers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customers.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("/customers")
    public ResponseEntity<List<Customers>> getAllCustomers(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("invoices-is-null".equals(filter)) {
            log.debug("REST request to get all Customerss where invoices is null");
            return new ResponseEntity<>(StreamSupport
                .stream(customersRepository.findAll().spliterator(), false)
                .filter(customers -> customers.getInvoices() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        if ("quotes-is-null".equals(filter)) {
            log.debug("REST request to get all Customerss where quotes is null");
            return new ResponseEntity<>(StreamSupport
                .stream(customersRepository.findAll().spliterator(), false)
                .filter(customers -> customers.getQuotes() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        if ("orders-is-null".equals(filter)) {
            log.debug("REST request to get all Customerss where orders is null");
            return new ResponseEntity<>(StreamSupport
                .stream(customersRepository.findAll().spliterator(), false)
                .filter(customers -> customers.getOrders() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Customers");
        Page<Customers> page = customersRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customers/:id} : get the "id" customers.
     *
     * @param id the id of the customers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customers> getCustomers(@PathVariable Long id) {
        log.debug("REST request to get Customers : {}", id);
        Optional<Customers> customers = customersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customers);
    }

    /**
     * {@code DELETE  /customers/:id} : delete the "id" customers.
     *
     * @param id the id of the customers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomers(@PathVariable Long id) {
        log.debug("REST request to delete Customers : {}", id);
        customersRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
