package jake.pizza.pizza_menu.models;

import java.time.DayOfWeek;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.lang.Nullable;

public class PizzaSpecial {

    private ObjectId id;

    private ObjectId pizzaId;

    private int newPriceInCents;

    @Nullable
    private DayOfWeek specialDay;

    @Nullable
    private Date expirationDate;


    public PizzaSpecial() {
    }

    public PizzaSpecial(ObjectId id, ObjectId pizzaId, int newPriceInCents, DayOfWeek specialDay, Date expirationDate) {
        this.id = id;
        this.pizzaId = pizzaId;
        this.newPriceInCents = newPriceInCents;
        this.specialDay = specialDay;
        this.expirationDate = expirationDate;
    }


    @Override
    public String toString() {
        // TODO: Add a formatter to include decimal and consider a more useful implementation in general
        return String.format("%s pizza, On sale for %s on %s", pizzaId.toHexString(), String.valueOf(newPriceInCents), specialDay.toString());
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(ObjectId pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getNewPriceInCents() {
        return newPriceInCents;
    }

    public void setNewPriceInCents(int newPriceInCents) {
        this.newPriceInCents = newPriceInCents;
    }

    public DayOfWeek getSpecialDay() {
        return specialDay;
    }

    public void setSpecialDay(DayOfWeek specialDay) {
        this.specialDay = specialDay;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


}
