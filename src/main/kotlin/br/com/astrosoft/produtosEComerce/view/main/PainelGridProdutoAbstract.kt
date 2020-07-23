package br.com.astrosoft.produtosEComerce.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.produtosEComerce.model.beans.Produto
import br.com.astrosoft.produtosEComerce.viewmodel.IProdutosEComerceView
import com.github.mvysny.karibudsl.v10.KeyShortcut
import com.github.mvysny.karibudsl.v10.addShortcut
import com.github.mvysny.karibudsl.v10.refresh
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode.MULTI
import com.vaadin.flow.component.grid.GridSortOrder
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant.LUMO_SMALL
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.provider.SortDirection
import com.vaadin.flow.data.value.ValueChangeMode.ON_CHANGE
import com.vaadin.flow.dom.DomEvent

abstract class PainelGridProdutoAbstract(view: IProdutosEComerceView, blockUpdate: () -> Unit):
  PainelGrid<Produto>(view, blockUpdate) {
  override fun Grid<Produto>.gridConfig() {
    setSelectionMode(MULTI)
    colCodigo()
    colBarcode()
   val colDescricao= colDescricao()
    colGrade()
    colMarca()
    colCategoria()
    val colDescricaoCompleta = colDescricaoCompleta()
    val colBitola = colBitola()
    val colImagem = colImagem()
    colPeso()
    colAltura()
    colComprimento()
    colLargura()
    val binder = Binder(Produto::class.java)
    editor.binder = binder
    val descricaoCompletaField = textAreaComponente()
    val bitolaField = textFieldComponente()
    val imagemField = textFieldComponente()
    
    binder.forField(descricaoCompletaField)
      .bind(Produto::descricaoCompleta.name)
    binder.forField(bitolaField)
      .bind(Produto::bitola.name)
    binder.forField(imagemField)
      .bind(Produto::imagem.name)
    
    colDescricaoCompleta.editorComponent = descricaoCompletaField
    colBitola.editorComponent = bitolaField
    colImagem.editorComponent = imagemField
    
    addItemDoubleClickListener {event ->
      editor.editItem(event.item)
      descricaoCompletaField.focus()
    }
    editor.addCloseListener {
      editor.refresh()
    }
    editor.addCloseListener {event->
      view.salvaProduto(binder.bean)
    }
    element.addEventListener("keyup") {event: DomEvent? -> editor.cancel()}.filter =
      "event.key === 'Escape' || event.key === 'Esc'"
    
    this.sort(listOf(GridSortOrder(colDescricao, SortDirection.ASCENDING)))
  }
  
  private fun textAreaComponente() = TextArea().apply {
    this.valueChangeMode = ON_CHANGE
    style.set("maxHeight", "50em");
    style.set("minHeight", "2em");
    setSizeFull()
  }
  
  private fun textFieldComponente() = TextField().apply {
    addThemeVariants(LUMO_SMALL)
    setSizeFull()
  }
}
