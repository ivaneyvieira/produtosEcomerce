package br.com.astrosoft.produtosECommerce.model.beans

import br.com.astrosoft.produtosECommerce.model.beans.Marca.Companion
import br.com.astrosoft.produtosECommerce.model.local

data class Bitola(var bitolaNo: Int = 0, var name: String = "") {
  companion object {
    private val listBitolas = mutableListOf<Bitola>().apply {
      addAll(local.findAllBitola())
    }
    
    fun findAll(): List<Bitola> {
      listBitolas.clear()
      listBitolas.addAll(local.findAllBitola())
      return listBitolas
    }
    
    fun findById(id: Int) = listBitolas
      .firstOrNull {it.bitolaNo == id}
    
    fun add(bitola: Bitola) {
      local.addBitola(bitola)
    }
    
    fun update(bitola: Bitola) {
      local.updateBitola(bitola)
    }
    
    fun delete(bitola: Bitola) {
      local.deleteBitola(bitola)
    }
  
    fun nextNo(): Int {
      val maxNo =
        findAll()
          .map {it.bitolaNo}
          .max() ?: 0
      return maxNo + 1
    }
  }
}