<template>
  <div class="container">
    <h2>Productos</h2>
    <form @submit.prevent="guardar">
      <div class="form-group">
        <label for="codigoBarras">Codigo Barras</label>
        <input type="text" v-model="form.codigoBarras" />
      </div>
      <div class="form-group">
        <label for="nombre">Nombre</label>
        <input type="text" v-model="form.nombre" />
      </div>
      <div class="form-group">
        <label for="categoria">Categoria</label>
        <input type="text" v-model="form.categoria" />
      </div>
      <div class="form-group">
        <label for="stock">Stock</label>
        <input type="number" v-model.number="form.stock" min="0" />
      </div>
      <div class="form-group">
        <label for="precio">Precio</label>
        <input type="number" v-model.number="form.precio" min="0" step="0.01" />
      </div>
      <button type="submit">{{ editId ? "Actualizar" : "Crear" }}</button>
    </form>
    <div v-if="error" class="error">{{ error }}</div>

    <div class="listado">
      <h3>Lista de Productos</h3>
      <button @click="cargarTodos" class="btn-reload">Recargar</button>

      <table v-if="lista.length">
        <thead>
          <tr>
            <th>ID</th>
            <th>Codigo</th>
            <th>Nombre</th>
            <th>Categoria</th>
            <th>Stock</th>
            <th>Precio</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in lista" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.codigoBarras }}</td>
            <td>{{ item.nombre }}</td>
            <td>{{ item.categoria }}</td>
            <td>{{ item.stock }}</td>
            <td>{{ item.precio }}</td>
            <td>
              <button @click="editar(item)">Editar</button>
              <button @click="eliminar(item.id)" class="btn-delete">
                Eliminar
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import {
  actualizar,
  crear,
  eliminar,
  consultarPorId,
  consultarTodos,
} from "@/clients/AppClient";

export default {
  data() {
    return {
      form: {
        codigoBarras: "",
        nombre: "",
        categoria: "",
        stock: 0,
        precio: 0,
      },
      editId: null,
      lista: [],
      busquedaId: null,
      resultadoBusqueda: null,
      error: null,
    };
  },
  methods: {
    async cargarTodos() {
      try {
        this.lista = await consultarTodos();
      } catch (error) {
        this.error = "Error al cargar datos";
      }
    },
    async guardar() {
      try {
        this.error = null;
        if (this.editId) {
          await actualizar(this.editId, this.form);
        } else {
          await crear(this.form);
        }
        this.form = {
          codigoBarras: "",
          nombre: "",
          categoria: "",
          stock: 0,
          precio: 0,
        };
        this.editId = null;
        this.cargarTodos();
      } catch (error) {
        this.error = "Error al guardar";
      }
    },
    editar(item) {
      this.editId = item.id;
      this.form = {
        codigoBarras: item.codigoBarras,
        nombre: item.nombre,
        categoria: item.categoria,
        stock: item.stock,
        precio: item.precio,
      };
    },
    async eliminar(id) {
      if (confirm("Eliminar este registro?")) {
        try {
          await eliminar(id);
          this.cargarTodos();
        } catch (error) {
          this.error = "Error al eliminar";
        }
      }
    },
    async buscarPorId() {
      try {
        this.resultadoBusqueda = await consultarPorId(this.busquedaId);
      } catch (error) {
        this.error = "No se encontro";
      }
    },
    limpiarBusqueda() {
      this.busquedaId = null;
      this.resultadoBusqueda = null;
    },
  },
  mounted() {
    this.cargarTodos();
  },
};
</script>

<style scoped>
.container {
  max-width: 900px;
  margin: 10px auto;
  padding: 10px;
}

.form-group {
  margin-bottom: 10px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 5px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 5px 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
}

button:hover {
  background-color: #3aa876;
}

.btn-delete {
  background-color: #d63031;
}

.btn-delete:hover {
  background-color: #c0392b;
}

.btn-reload {
  margin-bottom: 10px;
}

.error {
  color: #d63031;
  background-color: #ffe6e6;
  padding: 5px;
  border-radius: 5px;
  margin: 10px;
}

.listado {
  margin-top: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

th,
td {
  padding: 15px;
  border: 1px solid #ddd;
  text-align: left;
}

th {
  background-color: #f5f5f5;
}

</style>
