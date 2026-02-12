import axios from "axios";

const AUTH_URL = 'http://localhost:8082/auth/token';

export const obtenerToken = async (user, password) => {
   const response = await axios.get(AUTH_URL, {
      params: { user, password }
   });
   const token = response.data.accessToken;
   if (token) {
      localStorage.setItem("jwt_token", token);
      localStorage.setItem("isAuth", true);
   } else {
      localStorage.setItem("isAuth", false);
      console.log("Error de autenticacion")
   }
   return token;
}

export const cerrarSesion = () => {
   localStorage.removeItem("jwt_token");
   localStorage.removeItem("isAuth");
}

