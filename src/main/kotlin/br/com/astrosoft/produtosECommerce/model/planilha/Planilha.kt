package br.com.astrosoft.produtosECommerce.model.planilha

import br.com.astrosoft.produtosECommerce.model.beans.Produto
import com.github.nwillc.poink.workbook
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import java.io.ByteArrayOutputStream

class Planilha {
  private val campos: List<Campo<*>> = listOf(
    CampoString("id"),
    CampoString("tipo") {prd -> "sem-variacao\n"},
    CampoString("sku-pai"),
    CampoString("sku") {prd -> prd.codigo.trim()},
    CampoString("ativo") {prd -> "S"},
    CampoString("usado") {prd -> "N"},
    CampoString("ncm") {prd -> prd.ncm},
    CampoString("gtin") {prd -> prd.barcode},
    CampoString("nome") {prd -> prd.descricaoCompletaPlanilha()},
    CampoString("descricao-completa") {prd -> prd.especificacoes},
    CampoString("url-video-youtube"),
    CampoString("estoque-gerenciado") {prd -> "N"},
    CampoNumber("estoque-quantidade") {prd -> prd.saldoLoja4()},
    CampoString("estoque-situacao-em-estoque") {prd -> "imediata"},
    CampoString("estoque-situacao-sem-estoque") {prd -> "indisponivel"},
    CampoString("preco-sob-consulta") {prd -> "N"},
    CampoString("preco-custo"),
    CampoNumber("preco-cheio") {prd -> prd.price()},
    CampoNumber("preco-promocional") {prd -> prd.price()},
    CampoString("marca") {prd -> prd.marcaDesc},
    CampoNumber("peso-em-kg") {prd -> prd.peso},
    CampoNumber("altura-em-cm") {prd -> prd.altura},
    CampoNumber("largura-em-cm") {prd -> prd.largura},
    CampoNumber("comprimento-em-cm") {prd -> prd.comprimento},
    CampoString("categoria-nome-nivel-1") {prd -> prd.grupo()},
    CampoString("categoria-nome-nivel-2") {prd -> prd.departamento()},
    CampoString("categoria-nome-nivel-3") {prd -> prd.secao()},
    CampoString("categoria-nome-nivel-4"),
    CampoString("categoria-nome-nivel-5"),
    CampoString("imagem-1") {prd -> prd.imagem1()},
    CampoString("imagem-2") {prd -> prd.imagem2()},
    CampoString("imagem-3") {prd -> prd.imagem3()},
    CampoString("imagem-4") {prd -> prd.imagem4()},
    CampoString("imagem-5") {prd -> prd.imagem5()},
    CampoString("grade-genero"),
    CampoString("grade-tamanho-de-anelalianca"),
    CampoString("grade-tamanho-de-calca"),
    CampoString("grade-tamanho-de-camisacamiseta"),
    CampoString("grade-tamanho-de-capacete"),
    CampoString("grade-tamanho-de-tenis"),
    CampoString("grade-voltagem"),
    CampoString("grade-tamanho-juvenil-infantil"),
    CampoString("grade-produto-com-uma-cor"),
    CampoString("grade-produto-com-duas-cores"),
    CampoString(""),
    CampoString("url-antiga")
                                             )
  
  fun grava(listaProdutos: List<Produto>): ByteArray {
    val wb = workbook {
      val headerStyle = cellStyle("Header") {
        fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
        fillPattern = FillPatternType.SOLID_FOREGROUND
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val rowStyle = cellStyle("Row") {
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val stSemGrade = sheet("Produtos Sem Grade") {
        val headers = campos.map {it.header}
        row(headers, headerStyle)
        listaProdutos.filter {it.grade == ""}
          .sortedBy {it.codigo + it.grade}
          .forEach {produto ->
            val valores = campos.map {it.produceVakue(produto)}
            row(valores, rowStyle)
          }
      }
      val stComGrade = sheet("Produtos Com Grade") {
        val headers = campos.map {it.header}
        row(headers, headerStyle)
        listaProdutos.filter {it.grade != ""}
          .sortedBy {it.codigo + it.grade}
          .forEach {produto ->
            val valores = campos.map {it.produceVakue(produto)}
            row(valores, rowStyle)
          }
      }
      campos.forEachIndexed {index, campo ->
        stSemGrade.autoSizeColumn(index)
        stComGrade.autoSizeColumn(index)
      }
    }
    val outBytes = ByteArrayOutputStream()
    wb.write(outBytes)
    return outBytes.toByteArray()
  }
}

open class Campo<T: Any>(val header: String, val produceVakue: (Produto) -> T)

class CampoString(header: String, produceVakue: (Produto) -> String = {""}): Campo<String>(header, produceVakue)
class CampoNumber(header: String, produceVakue: (Produto) -> Double = {0.00}): Campo<Double>(header, produceVakue)