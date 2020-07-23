package br.com.astrosoft.produtosEComerce.view.main

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.produtosEComerce.model.beans.Categoria
import br.com.astrosoft.produtosEComerce.model.beans.Cl
import br.com.astrosoft.produtosEComerce.model.beans.Fornecedor
import br.com.astrosoft.produtosEComerce.model.beans.Marca
import br.com.astrosoft.produtosEComerce.model.beans.Produto
import br.com.astrosoft.produtosEComerce.model.beans.TypePrd
import br.com.astrosoft.produtosEComerce.model.local
import br.com.astrosoft.produtosEComerce.model.saci
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.comboBox
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.combobox.ComboBox.ItemFilter
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.renderer.TemplateRenderer
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT

fun Grid<Produto>.colCodigo() = addColumnString(Produto::codigo) {
  setHeader("Código")
}

fun Grid<Produto>.colGrade() = addColumnString(Produto::grade) {
  setHeader("Grade")
}

fun Grid<Produto>.colBarcode() = addColumnString(Produto::barcode) {
  setHeader("Código de barras")
}

fun Grid<Produto>.colDescricao() = addColumnString(Produto::descricao) {
  setHeader("Descrição")
}

fun Grid<Produto>.colMarca() = addColumnString(Produto::marcaDesc) {
  setHeader("Marca")
}

fun Grid<Produto>.colFornecedor() = addColumnString(Produto::fornecedor) {
  setHeader("Fornecedor")
}

fun Grid<Produto>.colTipo() = addColumnString(Produto::typeName) {
  setHeader("Tipo")
}

fun Grid<Produto>.colCl() = addColumnString(Produto::clname) {
  setHeader("Cl")
}

fun Grid<Produto>.colCategoria() = addColumnString(Produto::categoriaDesc) {
  setHeader("Categoria")
}

fun Grid<Produto>.colDescricaoCompleta() = addColumnString(Produto::descricaoCompleta) {
  setHeader("Descrição Completa")
  isAutoWidth = false
  width = "20em"
}

fun Grid<Produto>.colBitola() = addColumnString(Produto::bitola) {
  setHeader("Bitola")
  isAutoWidth = false
  width = "10em"
}

fun Grid<Produto>.colImagem() = addColumnString(Produto::imagem) {
  setHeader("Imagem")
  isAutoWidth = false
  width = "20em"
}

fun Grid<Produto>.colPeso() = addColumnDouble(Produto::peso) {
  setHeader("Peso")
}

fun Grid<Produto>.colAltura() = addColumnDouble(Produto::altura) {
  setHeader("Altura")
}

fun Grid<Produto>.colComprimento() = addColumnDouble(Produto::comprimento) {
  setHeader("Comprimento")
}

fun Grid<Produto>.colLargura() = addColumnDouble(Produto::largura) {
  setHeader("Largura")
}

//
fun HasComponents.codigoField(block: IntegerField.() -> Unit = {}) = integerField("Código") {
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  this.valueChangeMode = TIMEOUT
  block()
}

fun HasComponents.descricaoIField(block: TextField.() -> Unit = {}) = textField("Descrição Inicial") {
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  this.valueChangeMode = TIMEOUT
  block()
}

fun HasComponents.descricaoFField(block: TextField.() -> Unit = {}) = textField("Descrição Final") {
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  this.valueChangeMode = TIMEOUT
  block()
}

fun HasComponents.fornecedorField(block: ComboBox<Fornecedor>.() -> Unit = {}) = comboBox<Fornecedor>("Fornecedor") {
  val filter = ItemFilter {element: Fornecedor, filterString: String? ->
    filterString ?: return@ItemFilter true
    element.fornecedor.contains(filterString, ignoreCase = true) ||
    element.vendno.toString() == filterString
  }
  isClearButtonVisible = true
  this.setItems(filter, saci.listaFornecedores())
  setItemLabelGenerator {
    "${it.vendno} ${it.fornecedor}"
  }
  setRenderer(TemplateRenderer.of<Fornecedor>(
    "<div>[[item.vendno]]<br><small>[[item.fornecedor]]</small></div>")
                .withProperty("vendno", Fornecedor::vendno)
                .withProperty("fornecedor", Fornecedor::fornecedor))
  width = "15em"
  element.setAttribute("theme", "small")
  block()
}

fun HasComponents.tipoField(block: ComboBox<TypePrd>.() -> Unit = {}) = comboBox<TypePrd>("Tipo") {
  val filter = ItemFilter {element: TypePrd, filterString: String? ->
    filterString ?: return@ItemFilter true
    element.typeName.contains(filterString, ignoreCase = true) ||
    element.typeno.toString() == filterString
  }
  isClearButtonVisible = true
  this.setItems(filter, saci.listaType())
  setItemLabelGenerator {
    "${it.typeno} ${it.typeName}"
  }
  setRenderer(TemplateRenderer.of<TypePrd>(
    "<div>[[item.typeno]]<br><small>[[item.typeName]]</small></div>")
                .withProperty("typeno", TypePrd::typeno)
                .withProperty("typeName", TypePrd::typeName))
  width = "15em"
  element.setAttribute("theme", "small")
  block()
}

fun HasComponents.clField(block: ComboBox<Cl>.() -> Unit = {}) = comboBox<Cl>("Cl") {
  val filter = ItemFilter {element: Cl, filterString: String? ->
    filterString ?: return@ItemFilter true
    element.clname.contains(filterString, ignoreCase = true) ||
    element.clno.startsWith(filterString)
  }
  isClearButtonVisible = true
  this.setItems(filter, saci.listaCl())
  setItemLabelGenerator {
    "${it.clno} ${it.clname}"
  }
  setRenderer(TemplateRenderer.of<Cl>(
    "<div>[[item.clno]]<br><small>[[item.clname]]</small></div>")
                .withProperty("clno", Cl::clno)
                .withProperty("clname", Cl::clname))
  width = "18em"
  element.setAttribute("theme", "small")
  block()
}

fun HasComponents.categoriaField(block: ComboBox<Categoria>.() -> Unit = {}) = comboBox<Categoria>("Categoria") {
  extensionCategoria(block)
}

fun @VaadinDsl ComboBox<Categoria>.extensionCategoria(block: ComboBox<Categoria>.() -> Unit = {}) : ComboBox<Categoria>{
  val filter = ItemFilter {element: Categoria, filterString: String? ->
    filterString ?: return@ItemFilter true
    element.descricao.contains(filterString, ignoreCase = true) ||
    element.categoriaNo.toString() == filterString
  }
  isClearButtonVisible = true
  this.setItems(filter, local.listaCategoria())
  setItemLabelGenerator {
    "${it.categoriaNo} ${it.descricao}"
  }
  setRenderer(TemplateRenderer.of<Categoria>(
    "<div>[[item.categoriaNo]]<br><small>[[item.clname]]</small></div>")
                .withProperty("categoriaNo", Categoria::categoriaNo)
                .withProperty("descricao", Categoria::descricao))
  element.setAttribute("theme", "small")
  width = "20em"
  block()
  return this
}

fun HasComponents.marcaField(block: ComboBox<Marca>.() -> Unit = {}) = comboBox<Marca>("Marca") {
  extensionMarca(block)
}

fun @VaadinDsl ComboBox<Marca>.extensionMarca(block: ComboBox<Marca>.() -> Unit = {}) :ComboBox<Marca>{
  val filter = ItemFilter {element: Marca, filterString: String? ->
    filterString ?: return@ItemFilter true
    element.name.contains(filterString, ignoreCase = true) ||
    element.marcaNo.toString() == filterString
  }
  isClearButtonVisible = true
  this.setItems(filter, local.listaMarca())
  setItemLabelGenerator {
    "${it.marcaNo} ${it.name}"
  }
  element.setAttribute("theme", "small")
  width = "20em"
  block()
  return this
}
