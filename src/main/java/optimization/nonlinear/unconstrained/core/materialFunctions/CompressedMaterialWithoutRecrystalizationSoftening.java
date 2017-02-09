package optimization.nonlinear.unconstrained.core.materialFunctions;

import java.util.List;
import static java.lang.Math.*;

/**
 * Function (4) from
 * Testing of the inverse software for identification of
 rheological models of materials subjected to plastic deformation

 by
 D. SZELIGA, M. PIETRZYK
 Akademia Górniczo-Hutnicza, Mickiewicza

 */

public class CompressedMaterialWithoutRecrystalizationSoftening implements MaterialFunction {

    double r0;
    double k0;
    double n;
    double beta;
    double ks;
    double betas;
    double m;

    double constTemp;
    double constStrainRate;



    public double calculateMaterialStressInPoint(double experimentalDeformationValue) {
        double w = calculateW(experimentalDeformationValue);
        return sqrt(3)*(calculateMiddleBracketsPart(w,experimentalDeformationValue))*pow(sqrt(3)
                *constStrainRate,m);
    }

    //w*k0*epsilon^n*exp(beta/T)+(1-W)*Ks*exp(BetaS/T)
    public double calculateMiddleBracketsPart(double w, double experimentalDeformationValue){
        return calculateFirstPartOfSumInBrackets(w,experimentalDeformationValue)+calculateSecondPartOfSumInBrackets(w);
    }

    public double calculateFirstPartOfSumInBrackets(double w, double experimentalDeformationValue){
        return w*k0*pow(experimentalDeformationValue,
                n)*exp(beta/constTemp);
    }

    public double calculateSecondPartOfSumInBrackets(double w){
        return (1-w)*ks*exp(betas/constTemp);
    }





    public double calculateW(double deformation){
        return exp(-r0*deformation);
    }


    @Override
    @Deprecated
    public double calculateMaterialStressInPoint(List<Double> optimizedParameters, double experimentalDeformationValue) {
        return 0;
    }

    public double getR0() {
        return r0;
    }

    public void setR0(double r0) {
        this.r0 = r0;
    }

    public double getK0() {
        return k0;
    }

    public void setK0(double k0) {
        this.k0 = k0;
    }

    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getKs() {
        return ks;
    }

    public void setKs(double ks) {
        this.ks = ks;
    }

    public double getBetas() {
        return betas;
    }

    public void setBetas(double betas) {
        this.betas = betas;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getConstTemp() {
        return constTemp;
    }

    public void setConstTemp(double constTemp) {
        this.constTemp = constTemp;
    }

    public double getConstStrainRate() {
        return constStrainRate;
    }

    public void setConstStrainRate(double constStrainRate) {
        this.constStrainRate = constStrainRate;
    }
}
