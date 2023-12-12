package shadow.dev.spring.integration.service;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import shadow.dev.spring.annotation.IT;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.integration.IntegrationTestBase;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//@IT
@RequiredArgsConstructor
public class ICompanyRepositoryIT extends IntegrationTestBase {

    final CompanyRepository iCompanyRepository;

    @Test
    void when_param_notNull_should_company_with_only_one_company() {
        var google1 = new Company();
        google1.setId(1L);
        google1.setName("Google");
        iCompanyRepository.save(google1);

        var all = iCompanyRepository.findAll();
        assertEquals("", 3, all.size());

        var byName = iCompanyRepository.findByName("Google");
        assertTrue("", byName.isPresent());

        var byId = iCompanyRepository.findById(1L);
        assertTrue("", byId.isPresent());

        var google = iCompanyRepository.getByAnyParamsWhatCanBeNull(1L, "Google");
        assertEquals("", 1, google.size());
    }

    @Test
    void when_secondParamINull_should_company_with_all_companies() {
        var google1 = new Company();
        google1.setId(1L);
        google1.setName("Google");
        iCompanyRepository.save(google1);

        var all = iCompanyRepository.findAll();
        assertEquals("", 3, all.size());

        var byName = iCompanyRepository.findByName("Google");
        assertTrue("", byName.isPresent());

        var byId = iCompanyRepository.findById(1L);
        assertTrue("", byId.isPresent());

        var google = iCompanyRepository.getByAnyParamsWhatCanBeNull(null, null);
        assertEquals("", 3, google.size());
    }

    @Test
    void when_secondParamINull_should_company_by_name() {
        var google1 = new Company();
        google1.setId(1L);
        google1.setName("Google");
        google1.setPublicName("SOme");
        iCompanyRepository.save(google1);
        var google2 = new Company();
        google2.setId(2L);
        google2.setName("Google1");
        google2.setPublicName("SOme");
        iCompanyRepository.save(google2);
        var google3 = new Company();
        google3.setId(3L);
        google3.setName("Google2");
        google3.setPublicName("SOmeSOme");
        iCompanyRepository.save(google3);
        var google4 = new Company();
        google4.setId(4L);
        google4.setName("Google3");
        google4.setPublicName("SOmeSOme");
        iCompanyRepository.save(google4);

        var all = iCompanyRepository.findAll();
        assertEquals("", 4, all.size());

        var byName = iCompanyRepository.findBypublicNameIfNameNullReturnAllNative("SOme");
        assertEquals("", 2, byName.size());

        var byNameIfNameNull = iCompanyRepository.findBypublicNameIfNameNullReturnAllNative(null);
        assertEquals("", 4, byNameIfNameNull.size());
    }

//--------------------------------------------------------isWorldFamous-------------------------//
@Test
void when_param_isWorldFamous_notNull_should_company_with_only_one_company() {
    var google1 = new Company();
    google1.setId(1L);
    google1.setName("Google");
    google1.setIsWorldFamous(true);
    iCompanyRepository.save(google1);

    var all = iCompanyRepository.findAll();
    assertEquals("", 3, all.size());

    var byName = iCompanyRepository.findByName("Google");
    assertTrue("", byName.isPresent());

    var byId = iCompanyRepository.findById(1L);
    assertTrue("", byId.isPresent());

    var google = iCompanyRepository.findByIsWorldFamousIfNameNullReturnAllNative(true);
    assertEquals("", 1, google.size());
}

    @Test
    void when_secondParamINull_should_company_by_isWorldFamous() {
        var google1 = new Company();
        google1.setId(1L);
        google1.setName("Google");
        google1.setPublicName("SOme");
        google1.setIsWorldFamous(true);

        iCompanyRepository.save(google1);
        var google2 = new Company();
        google2.setId(2L);
        google2.setName("Google1");
        google2.setPublicName("SOme");
        google2.setIsWorldFamous(true);
        iCompanyRepository.save(google2);
        var google3 = new Company();
        google3.setId(3L);
        google3.setName("Google2");
        google3.setPublicName("SOmeSOme");
        google3.setIsWorldFamous(false);
        iCompanyRepository.save(google3);
        var google4 = new Company();
        google4.setId(4L);
        google4.setName("Google3");
        google4.setPublicName("SOmeSOme");
        google4.setIsWorldFamous(false);
        iCompanyRepository.save(google4);

        var all = iCompanyRepository.findAll();
        assertEquals("", 4, all.size());

        var byIsWorldFamous = iCompanyRepository.findByIsWorldFamousIfNameNullReturnAllNative(true);
        assertEquals("", 2, byIsWorldFamous.size());

        var byIsWorldFamousNull = iCompanyRepository.findByIsWorldFamousIfNameNullReturnAllNative(null);
        assertEquals("", 4, byIsWorldFamousNull.size());
    }


    //--------------------------------------------------------set of Public Name-------------------------//
    @Test
    void when_filterCollection_maybeIsNull_should_company_by_pablicName() {
        var google1 = new Company();
        google1.setId(1L);
        google1.setName("Google");
        google1.setPublicName("SOme1");
        iCompanyRepository.save(google1);
        var google2 = new Company();
        google2.setId(2L);
        google2.setName("Google1");
        google2.setPublicName("SOme2");
        var google3 = new Company();
        google3.setId(3L);
        google3.setName("Google2");
        google3.setPublicName("SOme3");
        iCompanyRepository.save(google3);
        var google4 = new Company();
        google4.setId(4L);
        google4.setName("Google3");
        google4.setPublicName("SOme4");
        iCompanyRepository.save(google4);

        var all = iCompanyRepository.findAll();
        assertEquals("", 4, all.size());

        var set1 = iCompanyRepository.findBypublicInNameCollectionNative(Set.of("SOme1"));
        assertEquals("", 1, set1.size());

        var set2 = iCompanyRepository.findBypublicInNameCollectionNative(Set.of("SOme1", "SOme2"));
        assertEquals("", 2, set2.size());

        var set3 = iCompanyRepository.findBypublicInNameCollectionNative(Set.of("SOme1", "SOme3", "SOme2"));
        assertEquals("", 3, set3.size());

    }

    @Test
    void when_filterCollection_isNull_should_company_all() {
        var google1 = new Company();
        google1.setId(1L);
        google1.setName("Google");
        google1.setPublicName("SOme1");
        iCompanyRepository.save(google1);
        var google2 = new Company();
        google2.setId(2L);
        google2.setName("Google1");
        google2.setPublicName("SOme2");
        iCompanyRepository.save(google2);
        var google3 = new Company();
        google3.setId(3L);
        google3.setName("Google2");
        google3.setPublicName("SOme3");
        iCompanyRepository.save(google3);
        var google4 = new Company();
        google4.setId(4L);
        google4.setName("Google3");
        google4.setPublicName("SOme4");
        iCompanyRepository.save(google4);

        var all = iCompanyRepository.findAll();
        assertEquals("", 4, all.size());

        var set1 = iCompanyRepository.findBypublicInNameCollectionIfCollectionNullThanAllNative(Set.of("SOme1"));
        assertEquals("", 1, set1.size());

        var set2 = iCompanyRepository.findBypublicInNameCollectionIfCollectionNullThanAllNative(Set.of("SOme1", "SOme2"));
        assertEquals("", 2, set2.size());

        var set3 = iCompanyRepository.findBypublicInNameCollectionIfCollectionNullThanAllNative(Set.of("SOme1", "SOme3", "SOme2"));
        assertEquals("", 3, set3.size());

//        var set4 = iCompanyRepository.findBypublicInNameCollectionIfCollectionNullThanAllNative(null);
        var set4 = iCompanyRepository.findBypublicInNameCollectionIfCollectionNullThanAllNativeSPEL(Set.of());
        assertEquals("", 4, set4.size());

//        var set5 = iCompanyRepository.findBypublicInNameCollectionIfCollectionNullThanAllNativeSPELWintCheckNull(null);
//        assertEquals("", 4, set5.size());
    }

}
