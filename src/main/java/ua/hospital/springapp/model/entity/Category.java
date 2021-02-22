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

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"nameEn", "nameUk"})
})
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String nameEn;
	
	@NotNull
	private String nameUk;
	
	public Category(String nameEn, String nameUk) {
		this.nameEn = nameEn;
		this.nameUk = nameUk;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", nameEn:")
				.append(nameEn)
				.append(", nameUk: ")
				.append(nameUk)
				.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Category) {
			Category category = (Category)obj;
			return this.id == category.getId() && this.nameEn == category.getNameEn() 
					&& this.nameUk == category.getNameUk();			
		}
		return super.equals(obj);
	}

}
