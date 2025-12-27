package DAO;

import Model.Offer;
import java.sql.SQLException;

public interface OfferDAO {

    Offer create(Offer offer) throws SQLException;

    Offer getOfferById(int id) throws SQLException;

    Offer update(Offer offer) throws SQLException;

    void delete(int id) throws SQLException;
}
