package library.model;

import java.io.Serializable;

/**
 * Класс,собержащий набор возможных типов Организации
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL,
    GOVERNMENT,
    OPEN_JOINT_STOCK_COMPANY;
}
