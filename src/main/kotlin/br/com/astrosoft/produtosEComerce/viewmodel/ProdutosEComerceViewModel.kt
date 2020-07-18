package br.com.astrosoft.produtosEComerce.viewmodel

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.produtosEComerce.model.beans.Categoria
import br.com.astrosoft.produtosEComerce.model.beans.Cl
import br.com.astrosoft.produtosEComerce.model.beans.EEditor
import br.com.astrosoft.produtosEComerce.model.beans.Fornecedor
import br.com.astrosoft.produtosEComerce.model.beans.Produto
import br.com.astrosoft.produtosEComerce.model.beans.TypePrd

class ProdutosEComerceViewModel(view: IProdutosEComerceView): ViewModel<IProdutosEComerceView>(view) {
  fun updateGridEditar() {
    view.updateGridEditar(listEditar())
  }
  
  private fun listEditar(): List<Produto> {
    val filtro = view.filtroEditar
    return Produto.listaProdutos(codigo = filtro.codigo,
                                 descricaoI = filtro.descricaoI,
                                 descricaoF = filtro.descricaoF,
                                 fornecedor = filtro.fornecedor,
                                 type = filtro.type,
                                 cl = filtro.cl,
                                 editado = EEditor.EDITAR,
                                 categoria = null)
  }
  
  fun updateGridEditado() {
    view.updateGridEditado(listEditado())
  }
  
  private fun listEditado(): List<Produto> {
    val filtro = view.filtroEditado
    return Produto.listaProdutos(codigo = filtro.codigo,
                                 descricaoI = filtro.descricaoI,
                                 descricaoF = filtro.descricaoF,
                                 fornecedor = filtro.fornecedor,
                                 type = filtro.type,
                                 cl = filtro.cl,
                                 editado = EEditor.EDITADO,
                                 categoria = filtro.categoria)
  }
}

interface IFiltroEditar {
  val codigo: Int
  val descricaoI: String
  val descricaoF: String
  val fornecedor: Fornecedor?
  val type: TypePrd?
  val cl: Cl?
}

interface IFiltroEditado {
  val codigo: Int
  val descricaoI: String
  val descricaoF: String
  val fornecedor: Fornecedor?
  val type: TypePrd?
  val cl: Cl?
  val categoria: Categoria?
}

interface IProdutosEComerceView: IView {
  fun updateGridEditar(itens: List<Produto>)
  fun updateGridEditado(itens: List<Produto>)
  
  val filtroEditar: IFiltroEditar
  val filtroEditado: IFiltroEditado
}
