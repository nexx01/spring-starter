package shadow.dev.spring.datatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import shadow.dev.spring.datatabase.entity.Company;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface CompanyRepository extends JpaRepository<Company,Long > {

    //Optional,Future,Entity


//    @Query(name = "Company.findByName")

    @Query("select c from Company c " +
//            "join fetch c.locales cl " +
            "where c.name = :name2")
    Optional<Company> findByName(@Param("name2") String name);

    //Collection,Stream(batch,close)
    List<Company> findAllByNameContainingIgnoreCase(String fragment);

    @Query(value = "select * from company as c where (:publicName is null or c.public_name = cast(:publicName as varchar))",nativeQuery = true)
    List<Company> findBypublicNameIfNameNullReturnAllNative(@Param("publicName") String name);

    @Query(value = "select * from company as c where (:isWorldFamous is null or c.is_world_famous = :isWorldFamous )",nativeQuery = true)
    List<Company> findByIsWorldFamousIfNameNullReturnAllNative(@Param("isWorldFamous") Boolean isWorldFamous);

    @Query(value = "select * from company as c where c.public_name in :publicNames",nativeQuery = true)
    List<Company> findBypublicInNameCollectionNative(@Param("publicNames") Set<String> publicNames);

    @Query(value = "select * from company as c where (COALESCE(:publicNames) is null or c.public_name in (:publicNames))",nativeQuery = true)
    List<Company> findBypublicInNameCollectionIfCollectionNullThanAllNative(@Param("publicNames") Set<String> publicNames);


    @Query(value = "select * from company as c where (:#{#publicNames.empty} or c.public_name in (:publicNames))", nativeQuery = true)
    List<Company> findBypublicInNameCollectionIfCollectionNullThanAllNativeSPEL(@Param("publicNames") Set<String> publicNames);

    //not work
//    @Query(value = "select * from company as c where (:#{#publicNames != null} or c.public_name in (:#{#publicNames != null?publicNames : Set.of()} ))", nativeQuery = true)
//    List<Company> findBypublicInNameCollectionIfCollectionNullThanAllNativeSPELWintCheckNull(@Param("publicNames") Set<String> publicNames);

   // Streamable<Company> findAll();

//    Stream<Company> findAllByCustomQueryAndStream();


    @Query(value = "select * from company as c where (:id is null or c.id = :id) AND (:name is null or c.name = :name)"
            ,nativeQuery = true)
    List<Company> getByAnyParamsWhatCanBeNull(
            @Param("id") Long id,
            @Param("name") String name
    );

//    Optional<Company> findById(@Param("id") Long id);

}
