package com.fedex.jms.poc;

import com.opencsv.bean.CsvBindByName;

public class MaterialComposition {
	
	@CsvBindByName
	private double fixed_acidity;
	
	@CsvBindByName
	private double volatile_acidity;
	
	@CsvBindByName
	private double citric_acid;
	
	@CsvBindByName
	private double residual_sugar;
	
	@CsvBindByName
	private double chlorides;
	
	@CsvBindByName
	private double free_sulfurdioxide;
	
	@CsvBindByName
	private double total_sulfurdioxide;
	
	@CsvBindByName
	private double density;
	
	@CsvBindByName
	private double pH;
	
	@CsvBindByName
	private double sulphates;
	
	@CsvBindByName
	private double alcohol;
	
	@CsvBindByName
	private double quality;
	
	public MaterialComposition(double fixed_acidity, double volatile_acidity, double citric_acid, double residual_sugar,
			double chlorides, double free_sulfurdioxide, double total_sulfurdioxide, double density, double pH, double sulphates,
			double alcohol, double quality) {
		super();
		this.fixed_acidity = fixed_acidity;
		this.volatile_acidity = volatile_acidity;
		this.citric_acid = citric_acid;
		this.residual_sugar = residual_sugar;
		this.chlorides = chlorides;
		this.free_sulfurdioxide = free_sulfurdioxide;
		this.total_sulfurdioxide = total_sulfurdioxide;
		this.density = density;
		this.pH = pH;
		this.sulphates = sulphates;
		this.alcohol = alcohol;
		this.quality = quality;
	}
	
	
	public double getFixed_acidity() {
		return fixed_acidity;
	}

	public void setFixed_acidity(double fixed_acidity) {
		this.fixed_acidity = fixed_acidity;
	}

	public double getVolatile_acidity() {
		return volatile_acidity;
	}

	public void setVolatile_acidity(double volatile_acidity) {
		this.volatile_acidity = volatile_acidity;
	}

	public double getCitric_acid() {
		return citric_acid;
	}

	public void setCitric_acid(double citric_acid) {
		this.citric_acid = citric_acid;
	}

	public double getResidual_sugar() {
		return residual_sugar;
	}

	public void setResidual_sugar(double residual_sugar) {
		this.residual_sugar = residual_sugar;
	}

	public double getChlorides() {
		return chlorides;
	}

	public void setChlorides(double chlorides) {
		this.chlorides = chlorides;
	}

	public double getFree_sulfurdioxide() {
		return free_sulfurdioxide;
	}

	public void setFree_sulfurdioxide(double free_sulfurdioxide) {
		this.free_sulfurdioxide = free_sulfurdioxide;
	}

	public double getTotal_sulfurdioxide() {
		return total_sulfurdioxide;
	}

	public void setTotal_sulfurdioxide(double total_sulfurdioxide) {
		this.total_sulfurdioxide = total_sulfurdioxide;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getpH() {
		return pH;
	}

	public void setpH(double pH) {
		this.pH = pH;
	}

	public double getSulphates() {
		return sulphates;
	}

	public void setSulphates(double sulphates) {
		this.sulphates = sulphates;
	}

	public double getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(double alcohol) {
		this.alcohol = alcohol;
	}


	public double getQuality() {
		return quality;
	}


	public void setQuality(double quality) {
		this.quality = quality;
	}

	
	

}
