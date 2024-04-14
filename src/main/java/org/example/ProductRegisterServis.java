package org.example;

import org.example.dataRequest.ProductRegisterRequest;
import org.example.dataRequest.ResponseData;
import org.example.entity.AccountEntity;
import org.example.entity.TppProductRegisterEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.example.repository.RepositoryAccount;
import org.example.repository.RepositoryProductRegistr;
import org.example.repository.RepositoryTppRefProductRegistryType;
import org.example.dataRequest.Enum.ProductRegisterState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductRegisterServis {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    RepositoryProductRegistr repositoryProductRegistr;
    @Autowired
    RepositoryTppRefProductRegistryType repositoryTppRefProductRegistryType;

    @Autowired
    RepositoryAccount repositoryAccount;
    public boolean checkDubl(int instanceId,String registryTypeCode)
    {   try {
        int count = repositoryProductRegistr.countByProductIdAndType
                (instanceId, registryTypeCode);
        return count>0;
        }
        catch (Exception e)
        {
            throw  new RuntimeException(" Ошибка при определени регистра продукта <"+instanceId+"> и типа регистра <"+registryTypeCode+ " > Error: " + e.getMessage());
        }
    }

    public Optional<TppRefProductRegisterTypeEntity> getType(String registryTypeCode)
    {
            Optional<TppRefProductRegisterTypeEntity> productTypeOptional =
                    repositoryTppRefProductRegistryType.findByvalue(registryTypeCode);
            return productTypeOptional;
    }
    public AccountEntity getAccount(String branchCode,String currencyCode,String mdmCode, String priorityCode,
                                    String registryTypeCode)
    {
        try {
            Pageable pageable = PageRequest.of(0, 1); //решил так попробовать  найти 1 запись
            Page<AccountEntity> accountPage = repositoryAccount.findAccountByPoolAttributes(branchCode,
                    currencyCode, mdmCode, priorityCode,
                    registryTypeCode, pageable);
            return accountPage.hasContent() ? accountPage.getContent().get(0) : null;
        }
        catch (Exception e)
        {
            throw  new RuntimeException(" Ошибка при определени счета  по параметрам: " +branchCode +"<" +branchCode + ">"+
                                        " CurrencyCode <"+ currencyCode+"> MdmCode <"+mdmCode+">" +
                                        " priorityCode <"+ priorityCode+"> RegisterType <" +registryTypeCode +"> Error: " + e.getMessage());
        }
    }
    public String getSchemaForTable (String table_name) {
        String query = "Select table_schema from information_schema.tables where table_name=?";
        //  queryForObject вернет только одно значени иначе ошибка
        //new Object[] {table_name} параметры;  String.class -ждем на выходе
        return jdbcTemplate.queryForObject(query, new Object[] {table_name}, String.class);
    }
    @Transactional
    public TppProductRegisterEntity createProductRegister (AccountEntity account, TppProductRegisterEntity productRegister )  {
        try {
            productRegister = repositoryProductRegistr.save(productRegister);
            account.setBussy(true);
            repositoryAccount.save(account);
            return productRegister;
        }
        catch (Exception e)
        {
            throw  new RuntimeException(" Ошибка при создании записи продуктового регистра productId=" +productRegister.getProductId()+
                    " type=" +productRegister.getType()+
                    " accountNumber =" + productRegister.getAccountNumber() +" Error :" +
                    e.getMessage() );
        }
    }
}
