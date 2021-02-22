package ua.hospital.springapp.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents Meal entity in webapp entity model.
 * 
 * @author Alexander-PC
 *
 */

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"nameEn", "nameUk"})
})
public class Meal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String nameEn;
	
	@NotNull
	private String nameUk;
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", nameEn: ")
				.append(nameEn)
				.append(", nameUk: ")
				.append(nameUk)
				.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Meal) {
			Meal meal = (Meal)obj;
			return this.id == meal.getId() && this.nameEn == meal.getNameEn() 
					&& this.nameUk == meal.getNameUk();			
		}
		return super.equals(obj);
	}

}
