package com.obu.tech.poba.press_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PressRepository extends JpaRepository<Press, Long>, JpaSpecificationExecutor<Press> {
    @Query("SELECT pi.pressId, p.prefix, p.prefixOther, p.name, p.surname, pi.pressName, pi.pressDate" +
            ",pi.pressTopic" +
            ",pi.pressSiteRef" +
            ",pi.pressPrint" +
            ",pi.pressTv " +
            ",pi.pressSatellite " +
            ",pi.pressOnline " +
            ",pi.guestPrefix1 "+
            ",pi.guestPrefixOther1 "+
            ",pi.guestName1 " +
            ",pi.guestSurname1"+
            ",pi.guestPrefix2 "+
            ",pi.guestPrefixOther2 "+
            ",pi.guestName2 " +
            ",pi.guestSurname2"+
            ",pi.guestPrefix3 "+
            ",pi.guestPrefixOther3 "+
            ",pi.guestName3 " +
            ",pi.guestSurname3 "+
            "FROM Press pi JOIN Profile p ON pi.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:pressDate is null or pi.pressDate >= :pressDate)")
    List<Object[]> findPressInfo(@Param("name") String name,
                                 @Param("pressDate") LocalDate pressDate);
}
