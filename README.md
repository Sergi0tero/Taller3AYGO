# Proyecto: Aplicación Spring Boot "Hello World" en Docker y AWS EC2

## 📘 Descripción General

En este proyecto se pide la creacion de una arquitectura de microservicios que simule en parte una plataforma tipo uber, donde se pueda administrar los viajes, usuarios y conductores de la aplicacion. Se pide el uso de API Gateway, Lambda y EC2.

---

## 🧱 Arquitectura del Proyecto

La arquitectura general se compone de los siguientes elementos:

- Cliente (En este caso se pueden hacer las consultas desde Postman)
- AWS API GATEWAY
- 4 Lambdas que manejan la logica
- EC2 donde se guarda la persistencia del aplicativo

**Estructura visual del sistema desplegado:**

<img width="952" height="680" alt="image" src="https://github.com/user-attachments/assets/cd83ad93-7027-42a3-b0cb-50b92727fe10" />

**[Diagrama de clases (Mermaid)](https://mermaid.live/edit#pako:eNrdWNtu2zgQ_RWCQIF4awfxLU70UKBJjNZA2jXs9KUwUNAiKwuVSC9JZeMN4m9f3qRKlug4RoAC0YMkz8yZGR4Oh5QfYcgwgQEMEyTETYwijtIFBeoyEvBNEA4erURfnbnkMY1AjOsyilJSlyKMORHit-J9ROQEn7SANSgphFFYcQvcs3IQjfqqAnhwRuVHfrRJeMC5to5_so8yIzc8vj-akyVjCUEULDOx-YOMxOJKJaCALp8q0uic5hkyZjEmh1LBeBzFtC4ntMEYG5br8kw0SYVEMnuVEvvbJOlBOqUfPaa-oFrjx9ma8kCd0o_Wa9SDNSo_cm5482Cd8qBFMUWblFB5aClwVTYT3Dy7FTlm2TIhAKUso_L5ec_la5vOFyJXrCGM5IgKFMqY0RskyWvUzcyMyIN0yv0z6EU75Z7mZthRaMvWTm-zSqt6vRrI0dMy0x4nVRu_r7vqtHi87VodVp-Ei1hIQkNyzajkLEl2evgXtN5aV21N-NbUomg2sCty65qUx0hP-tZUusfA0bLNq9VjNqGSRDqadnXLQqRHXqn5cEVwlhA8fiBhJhmfE34fhwQIpyg1zPchJ4o00xb0rWU2-J1yShIt1CVxqzjbGjrq9Xq1KRXljhcbxTUu-2i5jbMWy4qLaI7bpv5YiVjzlq1xEVPvYJXSbQPFaAPIJqqn6kTfWmZDq6WohUWCZl7ri7-S3I4Xm5oWNiWlbnV3-UQXLitWHdX4uLFTqyH8pQx8y8oO0JXaiXu28n5dG6mTF4PNa7Rx0VeG3Ogxd3e1MRTnts-7VojK1uVDWGadYi-5DU3BVPktSpcYNW1c0_FsPpnfjb9ej398nE5-fJvdlow-S7m-TmK96a2K15L-k2AUROpWynaFKE7IjPyTESFPlM9PKvl_0WbK2cPGicf3yk8b6C5FHtRM1azEWi1_YsxKrtUp2xC2x2lDO62u9rLae_B9Q4S5_nQMZb-b1jOkmdbxdigzy_hlhIUqo6jc_I7h29A4wQcQnjeQN8J5tX8fQ12ppTdZNPXyPXa7rXy_aaWH10z3Ne-DR6pKwL68ewdmJEGh2jSJUB-VkhN9jzEqTmDma3UBuwsIOp0P7s38wxEAbiPkp4NGU_fpHwBVaHFECQaSWfP8-2cXYdwE-oQnwE_G9xu7TFKVMFhuyqVdHVuoKpAk-qEPsggXfhsPueUof5WjUBTlxByGLIZ_BNYRcQQyZ2sH3ESMW_MKqsrHGpT3-NPTD56YAfh8dzcFIUoS57662b0EWe74L8HtNK7DoLANI_VdAAPJM9KGKeEp0j-h6XsLKFckJQsYqFeM-K8FXNAnhVkj-p2xNIdxlkUrGPxEiVC_3EHa_hdYSDmhmPBr_TEJg25_aJzA4BE-wKAzGJ72zy76o4v-5aB7OTgbteFGibvd07N-f3B5rq7esHd-OXxqw_9MYKXpnXVHo96gdzEaDPuD86f_ATGGc1M)**


---

## ⚙️ Configuración y uso

### Lambda creadas
<img width="2485" height="509" alt="image" src="https://github.com/user-attachments/assets/45c1256d-790a-41d2-a7ad-ee9111ec5c55" />
Como se puede evidenciar, se creo una por servicio, la cual contiene un handleRequest que se encarga de enrutar y manejar los procedimientos dentro de cada lambda

### Metodos disponibles en el apigateway
<img width="301" height="386" alt="image" src="https://github.com/user-attachments/assets/91eabfee-5a1b-463c-824e-005132a01563" />

### Creacion de maquina virtual usando Springbooot, docker y EC2
<img width="1547" height="161" alt="image" src="https://github.com/user-attachments/assets/6e9bff2d-b2f3-4d6a-8b32-b8f18b91c96b" />


### Ejemplo de uso

<img width="1359" height="928" alt="image" src="https://github.com/user-attachments/assets/1cf895af-de77-48e3-babb-d3037faaedb5" />

Hacemos la creacion de un nuevo usuario, en este caso, para la creacion requerimos el nombre y la direccion
<img width="1134" height="1238" alt="image" src="https://github.com/user-attachments/assets/2b7028fe-34ab-4dec-b408-e3d7db08d13b" />

Como podemos ver, se ejecuto y guardo correctamente el registro, ahora vamos a hacer un get de los usuarios registrados
<img width="1422" height="1199" alt="image" src="https://github.com/user-attachments/assets/4888a50c-d2a2-4ba4-bb8b-49751f74bbce" />

Se nos devuelve la lista de los usuarios registrados.

Este mismo proceso se puede realizar a la API usando postman y se puede realizar para cada uno de los componentes de la aplicacion



### 1. Clonar el proyecto y compilar con Maven
Comando para clonar el repositorio:
```bash
git clone https://github.com/Sergi0tero/Taller3AYGO.git
```

Comando para hacer compilar cada una de las aplicaciones maven creadas (lambda y springboot para persistencia)
```bash
mvn clean package
```

## 🧩 Conclusión

Este laboratorio demuestra la creacion de una version muy basica de una aplicacion tipo Uber. Haciendo uso de una arquitectura de microservicios implementada con los servicios brindados por AWS:
- Api Gateway
- Lambda
- EC2


**Autor:** Sergio Andrés Otero Herrera  
**Repositorio DockerHub:** [seanot26/persistence](https://hub.docker.com/repository/docker/seanot26/persistence/general)
