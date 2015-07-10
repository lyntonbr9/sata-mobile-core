package br.com.lle.sata.mobile.core.to;

import static br.com.lle.sata.mobile.core.util.StringUtil.concat;

import java.io.Serializable;
import java.math.BigDecimal;

public class CotacaoAtivoTO implements Comparable<CotacaoAtivoTO>, Serializable {
	
	private static final long serialVersionUID = 2267672898768261477L;

	private String codigo;

	private String periodo;
	
	private String abertura;
	
	private String maxima;
	
	private String minima;
	
	private String fechamento;
	
	private String tipoPeriodo;
	
	private String ano;
	
	private String volume;
	
	private double volatilidadeAnual;
	
	private double volatilidadeMensal;
	
	private int split;
	
	public String getId() {
		return concat(codigo, "-", periodo);
	}

	public BigDecimal getValorFechamento() {
		return new BigDecimal(Double.parseDouble(fechamento)/(100*split));
	}
	public BigDecimal getValorAbertura() {
		return new BigDecimal(Double.parseDouble(abertura)/(100*split));
	}
	public BigDecimal getValorMinima() {
		return new BigDecimal(Double.parseDouble(minima)/(100*split));
	}
	public BigDecimal getValorMaxima() {
		return new BigDecimal(Double.parseDouble(maxima)/(100*split));
	}
	
	public BigDecimal getValorVolatilidadeAnual() {
		return new BigDecimal(volatilidadeAnual);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof CotacaoAtivoTO))
			return false;
		return ((CotacaoAtivoTO)other).codigo.equals(codigo) 
			&& ((CotacaoAtivoTO)other).periodo.equals(periodo);
	}
	
	@Override
	public int compareTo(CotacaoAtivoTO other) {
		return 0;
		//TODO: Implementar a comparacao
//		return new CompareToBuilder()
//	       .append(codigo, other.codigo)
//	       .append(periodo, other.periodo)
//	       .toComparison();
	}
	
	@Override
	public String toString() {
		return concat(codigo, " ", periodo, " = ", fechamento);
	}
	
	public double getVolatilidadeAnual() {
		return volatilidadeAnual;
	}

	public void setVolatilidadeAnual(double volatilidadeAnual) {
		this.volatilidadeAnual = volatilidadeAnual;
	}

	public double getVolatilidadeMensal() {
		return volatilidadeMensal;
	}

	public void setVolatilidadeMensal(double volatilidadeMensal) {
		this.volatilidadeMensal = volatilidadeMensal;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getTipoPeriodo() {
		return tipoPeriodo;
	}

	public void setTipoPeriodo(String tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}

	public String getAbertura() {
		return abertura;
	}

	public void setAbertura(String abertura) {
		this.abertura = abertura;
	}

	public String getMaxima() {
		return maxima;
	}

	public void setMaxima(String maxima) {
		this.maxima = maxima;
	}

	public String getMinima() {
		return minima;
	}

	public void setMinima(String minima) {
		this.minima = minima;
	}

	public String getFechamento() {
		return fechamento;
	}

	public void setFechamento(String fechamento) {
		this.fechamento = fechamento;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public int getSplit() {
		return split;
	}

	public void setSplit(int split) {
		this.split = split;
	}
}
