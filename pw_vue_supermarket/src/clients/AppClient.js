import axios from "axios";

const API_URL = 'http://localhost:8080/api/v1.0';

const apiClient = axios.create({
   baseURL: API_URL,
   headers: {
      'Content-Type': 'application/json'
   }
})

// apiClient.interceptors.request.use(
//    (config) => {
//       const token = localStorage.getItem('jwt_token');
//       if (token) {
//          config.headers.Authorization = `Bearer ${token}`;
//       }
//       return config;
//    },
//    (error) => {
//       return Promise.reject(error);
//    }
// );

export const consultarTodos = async () => {
   return (await apiClient.get('/productos')).data;
}

export const consultarPorId = async (id) => {
   return (await apiClient.get(`/productos/${id}`)).data;
}

export const crear = async (producto) => {
   return (await apiClient.post('/productos', producto)).data;
}

export const actualizar = async (id, producto) => {
   return (await apiClient.put(`/productos/${id}`, producto)).data;
}
export const actualizar_parcial = async (id, producto) => {
   return (await apiClient.patch(`/productos/${id}`, producto)).data;
}

export const eliminar = async (id) => {
   return (await apiClient.delete(`/productos/${id}`)).data;
}
export const buscarPorCodigoBarras = async (codigoBarras) => {
   return (await apiClient.get(`/productos/buscar/${codigoBarras}`)).data;
}
export const crearVenta = async (venta) => {
   return (await apiClient.post('/ventas', venta)).data;
}

export const obtenerTodas = async () => {
   return (await apiClient.get('/ventas')).data;
}

export default apiClient;