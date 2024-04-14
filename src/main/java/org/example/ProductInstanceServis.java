package org.example;

import org.example.dataRequest.Enum.ProductRateType;
import org.example.dataRequest.Enum.ProductRegisterState;
import org.example.dataRequest.Enum.ProductRegistryType;
import org.example.dataRequest.InstanceArrangement;
import org.example.dataRequest.ProductInstanceRequest;
import org.example.dataRequest.ResponseData;
import org.example.entity.*;
import org.example.exception.OtherException;
import org.example.repository.RepositoryAgreement;
import org.example.repository.RepositoryProduct;
import org.example.repository.RepositoryProductClass;
import org.example.repository.RepositoryTppRefProductRegistryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.example.dataRequest.ResponceBuilder.*;


@Service
public class ProductInstanceServis {
    @Autowired
    RepositoryProduct repositoryProduct;
    @Autowired
    RepositoryAgreement repositoryAgreement;
    @Autowired
    RepositoryProductClass repositoryProductClass;
    @Autowired
    RepositoryTppRefProductRegistryType repositoryTppRefProductRegistryType;
    @Autowired
    ProductRegisterServis productRegisterServis;

    public  Optional<TppProductEntity>  checkDublProduct( String contractNumber)
    {

        System.out.println( " ищем по number="+contractNumber);
//request.getContractNumber()
        Optional<TppProductEntity> optionalProduct= repositoryProduct.findByNumber(contractNumber);
        return optionalProduct;
    }
    public Optional<TppProductEntity> findById(Integer instanceId) //ProductInstanceRequest request)
    {
        System.out.println( " ищем по instanceId="+instanceId) ; //request.getInstanceId());

        Optional<TppProductEntity> optionalProduct= repositoryProduct.findById(instanceId); //request.getInstanceId());
        return optionalProduct;
    }
    public Optional<AgreementEntity> findByNumber(String  numberArrangement)
    {
        System.out.println( " ищем по  agreement по  number="+numberArrangement);

        Optional<AgreementEntity> optionalAgreement= repositoryAgreement.findByNumber(numberArrangement);
        return optionalAgreement;
    }

    public String checkDublAgreement (List<InstanceArrangement>  instanceAgreements)
    {
        System.out.println("Проверка instanceAgreements");
        String errorMessage="";
        int kol=0;
        for (InstanceArrangement instanceArrangement :  instanceAgreements )
        {   /// проверяем всю коллекцию
            kol=kol+1;
            System.out.println("kol="+kol);
            Optional<AgreementEntity> optionalAgreement = findByNumber(instanceArrangement.getNumber());
            if (optionalAgreement.isPresent()) {
                AgreementEntity agreement = optionalAgreement.get();
                //если найдены
                errorMessage =errorMessage + "\n Строка <"+kol +"> :"+
                        "Параметр № Дополнительного соглашения (сделки) Number <"+
                        instanceArrangement.getNumber()+ "> уже существует для ЭП с ИД  <"+
                           agreement.getProductId()+">.";
            }
        }
        System.out.println("Проверка instanceAgreements : Пройдена ");
        return errorMessage;
    }
    @Transactional
    public ResponseData createAgreementIds (Integer productId, List<InstanceArrangement> instanceAgreements)
    {
        ResponseData responseData = createResponseData();
        List<Integer> agreementId = responseData.getAgreementId();
        responseData.setProductId(productId);
        try {
            for (InstanceArrangement requestAgreement : instanceAgreements) {
                AgreementEntity agreement = new AgreementEntity();
                agreement.setProductId(productId);
                agreement.setGeneralAgreementId(requestAgreement.getGeneralAgreementId());
                agreement.setSupplementaryAgreementId(requestAgreement.getSupplementaryAgreementId());
                agreement.setArrangementType(ProductRegistryType.fromValue(requestAgreement.getArrangementType()).name());
                agreement.setShedulerJobId((long) requestAgreement.getShedulerJobId());
                agreement.setNumber(requestAgreement.getNumber());
                agreement.setOpeningDate(convertDateToTimeStamp(requestAgreement.getOpeningDate()));
                agreement.setClosingDate(convertDateToTimeStamp(requestAgreement.getClosingDate()));
                agreement.setCancelDate(convertDateToTimeStamp(requestAgreement.getCancelDate()));
                agreement.setValidityDuration((long) requestAgreement.getValidityDuration());
                agreement.setCancellationReason(requestAgreement.getCancellationReason());
                agreement.setStatus(ProductRegisterState.fromValue(requestAgreement.getStatus()).name());
                agreement.setInterestCalculationDate(convertDateToTimeStamp(requestAgreement.getInterestCalculationDate()));
                agreement.setInterestRate(BigDecimal.valueOf(requestAgreement.getInterestRate()));
                agreement.setCoefficient(BigDecimal.valueOf(requestAgreement.getCoefficient()));
                agreement.setCoefficientAction(requestAgreement.getCoefficientAction());
                agreement.setMinimumInterestRate(BigDecimal.valueOf(requestAgreement.getMinimumInterestRate()));
                agreement.setMinimumInterestRateCoefficient(BigDecimal.valueOf(requestAgreement.getMinimumInterestRateCoefficient()));
                agreement.setMinimumInterestRateCoefficientAction(requestAgreement.getMinimumInterestRateCoefficientAction());
                agreement.setMaximalInterestRate(BigDecimal.valueOf(requestAgreement.getMaximalnterestRate()));
                agreement.setMaximalInterestRateCoefficient(BigDecimal.valueOf(requestAgreement.getMaximalnterestRateCoefficient()));
                agreement.setMaximalInterestRateCoefficientAction(requestAgreement.getMaximalnterestRateCoefficientAction());
                repositoryAgreement.save(agreement);
                agreementId.add(agreement.getId());
            }
        }
        catch(Exception e) {
            throw new RuntimeException(" Ошибка при сздание экземпляра agreement " +e.getMessage());
    }

        return responseData;

    }
    public Optional<TppRefProductClassEntity> findByValue(String productCode)
    {
      //  System.out.println( " ищем по ProductCode ="+productCode);

        Optional<TppRefProductClassEntity> optionalProductClass= repositoryProductClass.findByValue(productCode);
        return optionalProductClass;
    }
    public List<TppRefProductRegisterTypeEntity> findByValueAndAccountType (String productCode, String accountType)
    {

        List<TppRefProductRegisterTypeEntity> productRegistryType=
                repositoryTppRefProductRegistryType.findByValueAndAccountType(productCode,accountType);
        return productRegistryType;
    }

    public ResponseData createResponseData ()
    {
        List<Integer> registerId =new ArrayList<>();
        List<Integer>  supplementaryAgreementId =new ArrayList<>();
        ResponseData responseData =new ResponseData(null,registerId,supplementaryAgreementId);
        return responseData;
    }

    @Transactional
    public ResponseData createProductIdAndListProductRegisterID  (Long productClassId,
                                                           List<TppRefProductRegisterTypeEntity> productRegistryTypes,
                                                           ProductInstanceRequest request)
     {
        ResponseData responseData = createResponseData();
        List<Integer> registerId = responseData.getRegisterId();
        TppProductEntity product = createProduct(productClassId, request);
        responseData.setProductId(product.getId());
        try
        {
            for (TppRefProductRegisterTypeEntity productRegistryType : productRegistryTypes) {
                // надо найти свободный счет в  accountpool для продукта

                AccountEntity account = productRegisterServis.getAccount(request.getBranchCode(),
                        request.getIsoCurrencyCode(), request.getMdmCode(), request.getUrgencyCode(),
                        request.getRegisterType());
                if (account == null) {
                    throw new OtherException(" нет свободного счета для договора " + request.getContractNumber());
                }
                TppProductRegisterEntity productRegister = new TppProductRegisterEntity();
                productRegister.setProductId((long) product.getId());
                productRegister.setType(productRegistryType.getValue());//                          //request.getRegisterType());
                productRegister.setAccount((long) account.getId());
                productRegister.setCurrencyCode(request.getIsoCurrencyCode());
                productRegister.setState(ProductRegisterState.OPEN.name());
                productRegister.setAccountNumber(account.getAccountNumber());
                productRegister = productRegisterServis.createProductRegister(account, productRegister);
                registerId.add(productRegister.getId());
            }
        }
        catch (OtherException e ) {
         throw  e;  // бросаем наверх
        }
        catch (Exception e)    {
            throw  new RuntimeException(" Ошибка при создании экземпляра продуктв:  " + e.getMessage());
        }
        return responseData;
    }

    @Transactional
    public TppProductEntity createProduct (Long id_tppRefProductClass,
                                           ProductInstanceRequest request)  {
        try {
               TppProductEntity product = new TppProductEntity();
                product.setProductCodeId(id_tppRefProductClass);
                product.setClientId(Long.parseLong(request.getMdmCode()));
                product.setType(ProductRegistryType.fromValue(request.getProductType()).name());
                product.setNumber(request.getContractNumber());
                product.setPriority(request.getPriority().longValue());
                product.setDateOfConclusion(convertDateToTimeStamp(request.getContractDate()));
                product.setThresholdAmount(BigDecimal.valueOf(request.getThresholdAmount()));
                product.setInterestRateType(ProductRateType.fromValue(request.getRateType()).name());
                product.setTaxRate(BigDecimal.valueOf(request.getTaxPercentageRate()));
                product.setPenaltyRate(BigDecimal.valueOf(request.getInterestRatePenalty()));
                product.setState(ProductRegisterState.OPEN.name());
                repositoryProduct.save(product);
            return product;
        }
        catch (Exception e)
        {
            throw  new RuntimeException(" Ошибка при созданни экземпляра продукта c номером =" +request.getContractNumber()+" Error :" +
                    e.getMessage());
        }
//           не все поля определил для product
//                	id serial PRIMARY KEY,
//	                product_code_id BIGINT,   +
//	                client_id BIGINT,         +
//	                type VARCHAR(50),          +
//	                number VARCHAR(50),        +
//	                priority BIGINT,           +
//	                date_of_conclusion TIMESTAMP,   ----?
//	                start_date_time TIMESTAMP,
//	                 end_date_time TIMESTAMP,
//	                 days BIGINT,
//	                 penalty_rate DECIMAL,        +
//	                 nso DECIMAL,
//	                 threshold_amount DECIMAL,    +
//	                  requisite_type VARCHAR(50),
//	                 interest_rate_type VARCHAR(50),    +
//	                  tax_rate DECIMAL,                +
//                     reasone_close VARCHAR(100),
//                       state VARCHAR(50)            +
//

    }
}
