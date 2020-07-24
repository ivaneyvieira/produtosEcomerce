package br.com.astrosoft.produtosECommerce.view.main

import br.com.astrosoft.produtosECommerce.model.beans.Cl
import br.com.astrosoft.produtosECommerce.model.beans.EEditor.IMPORTADO
import br.com.astrosoft.produtosECommerce.model.beans.Fornecedor
import br.com.astrosoft.produtosECommerce.model.beans.Produto
import br.com.astrosoft.produtosECommerce.model.beans.TypePrd
import br.com.astrosoft.produtosECommerce.viewmodel.IFiltroImportado
import br.com.astrosoft.produtosECommerce.viewmodel.IProdutosEComerceView
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField

class PainelGridProdutoImportado(view: IProdutosEComerceView, blockUpdate: () -> Unit):
  PainelGridProdutoAbstract(view, blockUpdate) {
  override fun statusDefault(): Int = IMPORTADO.value
  
  override fun updateGrid(itens: List<Produto>) {
    super.updateGrid(itens)
    val filter = filterBar as? IFiltroImportado
    if(filter?.isEmpty() == false)
      itens.forEach(grid::select)
  }
  
  override fun filterBar() = FilterBarImportado()
  
  inner class FilterBarImportado: FilterBar(), IFiltroImportado {
    private lateinit var edtCl: ComboBox<Cl>
    private lateinit var edtTipo: ComboBox<TypePrd>
    private lateinit var edtFornecedor: ComboBox<Fornecedor>
    private lateinit var edtDescricaoF: TextField
    private lateinit var edtDescricaoI: TextField
    private lateinit var edtCodigo: IntegerField
    
    override fun FilterBar.contentBlock() {
      button("Processar") {
        icon = VaadinIcon.COG.create()
        addThemeVariants(ButtonVariant.LUMO_SMALL)
        onLeftClick {
          view.processaProdutos(multiSelect())
        }
      }
      edtCodigo = codigoField {
        addValueChangeListener {blockUpdate()}
      }
      edtDescricaoI = descricaoIField {
        addValueChangeListener {blockUpdate()}
      }
      edtDescricaoF = descricaoFField {
        addValueChangeListener {blockUpdate()}
      }
      edtFornecedor = fornecedorField {
        addValueChangeListener {blockUpdate()}
      }
      edtTipo = tipoField {
        addValueChangeListener {blockUpdate()}
      }
      edtCl = clField {
        addValueChangeListener {blockUpdate()}
      }
    }
    
    override val codigo: Int
      get() = edtCodigo.value ?: 0
    override val descricaoI: String
      get() = edtDescricaoI.value ?: ""
    override val descricaoF: String
      get() = edtDescricaoF.value ?: ""
    override val fornecedor: Fornecedor?
      get() = edtFornecedor.value
    override val type: TypePrd?
      get() = edtTipo.value
    override val cl: Cl?
      get() = edtCl.value
  }
}


