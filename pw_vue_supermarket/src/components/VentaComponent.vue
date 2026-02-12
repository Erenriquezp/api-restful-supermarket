<template>
  <div class="container">
    <h2>Venta</h2>

    <div class="venta-container">
      <div>
      <label>Numero Venta</label>
      <input type="text" v-model.trim="venta.numVenta" />
    </div>
    <div>
      <label>Cedula</label>
      <input type="text" v-model.trim="venta.cedula" />
    </div>
    </div>

    <h3>Agregar producto</h3>
    <div>
      <label>Codigo Barras</label>
      <input type="text" v-model.trim="detalle.codigoBarras" />
    </div>
    <div>
      <label>Cantidad</label>
      <input type="number" v-model.number="detalle.cantidad" min="1" />
    </div>
    <button type="button" @click="agregar">Agregar</button>

    <div v-if="error">{{ error }}</div>

    <h3>Detalle</h3>
    <div v-if="!detalles.length">Sin productos</div>
    <table v-else>
      <thead>
        <tr>
          <th>Codigo</th>
          <th>Nombre</th>
          <th>Precio</th>
          <th>Cantidad</th>
          <th>Subtotal</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in detalles" :key="item.codigoBarras">
          <td>{{ item.codigoBarras }}</td>
          <td>{{ item.nombre }}</td>
          <td>{{ item.precio }}</td>
          <td>{{ item.cantidad }}</td>
          <td>{{ item.precio * item.cantidad }}</td>
        </tr>
      </tbody>
    </table>
    <button type="button" @click="guardar">Guardar</button>
    <hr />
    <h3>Ventas Realizadas</h3>
    <div v-if="!ventas.length" class="empty-message">
      No hay ventas registradas
    </div>
    <table v-else class="ventas-table">
      <thead>
        <tr>
          <th>Número Venta</th>
          <th>Cédula</th>
          <th>Total</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="venta in ventas" :key="venta.numVenta">
          <td>{{ venta.numVenta }}</td>
          <td>{{ venta.cedula }}</td>
          <td>${{ venta.totalVenta }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {
  buscarPorCodigoBarras,
  crearVenta,
  obtenerTodas,
} from "@/clients/AppClient";

export default {
  data() {
    return {
      venta: {
        numVenta: "",
        cedula: "",
        totalVenta: 0,
      },
      detalle: {
        codigoBarras: "",
        cantidad: 1,
      },
      detalles: [],
      error: "",
      ventas: [],
    };
  },
  methods: {
    async agregar() {
      this.error = "";
      try {
        const producto = await buscarPorCodigoBarras(this.detalle.codigoBarras);
        this.detalles.push({
          codigoBarras: this.detalle.codigoBarras,
          cantidad: this.detalle.cantidad,
          nombre: producto?.nombre || "",
          precio: producto?.precio || 0,
        });
        this.detalle = { codigoBarras: "", cantidad: 1 };
      } catch (err) {
        this.error = "Stock no disponible";
      }
    },
    async guardar() {
      this.error = "";
      if (!this.venta.numVenta || !this.venta.cedula || !this.detalles.length) {
        this.error = "Complete venta y agregue productos";
        return;
      }
      try {
        const payload = {
          numVenta: this.venta.numVenta,
          cedula: this.venta.cedula,
          productos: this.detalles.map((item) => ({
            codigoBarras: item.codigoBarras,
            cantidad: item.cantidad,
          })),
        };
        await crearVenta(payload);
        this.venta = { numVenta: "", cedula: ""};
        this.detalles = [];
      } catch (err) {
        this.error = "Error al guardar";
      }
    },
    async cargarDatos() {
      this.ventas = await obtenerTodas();
    },
  },
  mounted() {
    this.cargarDatos();
  },
};
</script>

<style scoped>

.container {
   max-width: 800px;
   margin: 0 auto;
   padding: 2rem;
   background-color: #f9f9f9;
   border-radius: 8px;
   box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.venta-container {
   display: flex;
   gap: 2rem;
   margin-bottom: 2rem;
}

div {
   margin-bottom: 1rem;
}

label {
   display: block;
   font-weight: 600;
   margin-bottom: 0.5rem;
   color: #333;
}

input {
   padding: 0.5rem;
   border: 1px solid #ddd;
   border-radius: 4px;
   font-size: 1rem;
   width: 100%;
   max-width: 300px;
}

button {
   padding: 0.75rem 1.5rem;
   background-color: #007bff;
   color: white;
   border: none;
   border-radius: 4px;
   cursor: pointer;
   font-size: 1rem;
}

button:hover {
   background-color: #0056b3;
}

h2 {
   color: #333;
   margin-bottom: 1.5rem;
}

h3 {
   color: #555;
   margin-top: 2rem;
   margin-bottom: 1rem;
}

table {
   width: 100%;
   border-collapse: collapse;
   margin: 1rem 0;
}

thead {
   background-color: #f8f9fa;
}

th, td {
   padding: 0.75rem;
   text-align: left;
   border-bottom: 1px solid #ddd;
}

th {
   font-weight: 600;
   color: #333;
}

tr:hover {
   background-color: #f5f5f5;
}

.error {
   color: #dc3545;
   font-weight: 600;
   margin: 1rem 0;
}

.empty-message {
   color: #999;
   font-style: italic;
   padding: 1rem;
}

hr {
   margin: 2rem 0;
   border: none;
   border-top: 1px solid #ddd;
}
</style>
