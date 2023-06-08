package com.usuarios.usuarios;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import https.t4is_uv_mx.clientes.BusquedaPedidoIDRequest;
import https.t4is_uv_mx.clientes.BusquedaPedidoIDResponse;
import https.t4is_uv_mx.clientes.ListaInventarioResponse;
import https.t4is_uv_mx.clientes.ListaPedidosResponse;
import https.t4is_uv_mx.clientes.RegistroRequest;
import https.t4is_uv_mx.clientes.RegistroResponse;

@Endpoint
public class EndPoint {
    @Autowired
    private iClientes iclientes;

    @PayloadRoot(localPart = "RegistroRequest", namespace = "https://t4is.uv.mx/clientes")

    @ResponsePayload
    public RegistroResponse Registro(@RequestPayload RegistroRequest peticion) {
        RegistroResponse response = new RegistroResponse();

        Pedidos cliente = new Pedidos();
        cliente.setNombre(peticion.getNombre());
        cliente.setFolio(peticion.getFolio());
        cliente.setCantidad(peticion.getCantidad());
        cliente.setTotal(peticion.getTotal());
        iclientes.save(cliente);

        response.setRespuesta("Pedido Registrado");

        return response;
    }

    @PayloadRoot(localPart = "BusquedaPedidoIDRequest", namespace = "https://t4is.uv.mx/clientes")
    @ResponsePayload
    public BusquedaPedidoIDResponse buscarId(@Validated @RequestPayload BusquedaPedidoIDRequest peticion) {
        BusquedaPedidoIDResponse response = new BusquedaPedidoIDResponse();
        Pedidos pedido = iclientes.findById(peticion.getId()).get();

        if (peticion.getId() == 0) {
            response.setStatus("400 Bad Request");
        } else {
            if (peticion.getId() == pedido.getId())
                response.setStatus("200 OK");
            response.setNombre(pedido.getNombre());
            response.setFolio(pedido.getFolio());
            response.setCantidad(pedido.getCantidad());
            response.setTotal(pedido.getTotal());
        }
        return response;
    }

    @PayloadRoot(localPart = "ListaPedidosRequest", namespace = "https://t4is.uv.mx/clientes")
    @ResponsePayload
    public ListaPedidosResponse listar() {
        ListaPedidosResponse response = new ListaPedidosResponse();
        Iterable<Pedidos> listaClientes = iclientes.findAll();

        for (Pedidos pedido : listaClientes) {
            ListaPedidosResponse.Pedidos e = new ListaPedidosResponse.Pedidos();
            if (pedido.getNombre().isEmpty() || pedido.getFolio().isEmpty()) {
                e.setStatus("400 Bad Request");
            } else {
                e.setStatus("200 OK");
                e.setId(pedido.getId());
                e.setNombre(pedido.getNombre());
                e.setFolio(pedido.getFolio());
                e.setCantidad(pedido.getCantidad());
                e.setTotal(pedido.getTotal());
            }
            response.getPedidos().add(e);
        }
        return response;
    }

    @Autowired
    private iInventarios inventarios;

    @PayloadRoot(localPart = "ListaInventarioRequest", namespace = "https://t4is.uv.mx/clientes")
    @ResponsePayload
    public ListaInventarioResponse listarInven() {
        ListaInventarioResponse response = new ListaInventarioResponse();
        Iterable<Inventario> listaInventario = inventarios.findAll();
        for (Inventario inventario : listaInventario) {
            ListaInventarioResponse.Inventario i = new ListaInventarioResponse.Inventario();
            if (inventario.getNombre().isEmpty() || inventario.getFolio().isEmpty()) {
                i.setStatus("400 Bad Request");
            } else {
                i.setStatus("200 OK");
                i.setId(BigInteger.valueOf(inventario.getId()));
                i.setNombre(inventario.getNombre());
                i.setFolio(inventario.getFolio());
                //i.setCantidad(BigInteger.valueOf());
                i.setTotal(Integer.toString(inventario.getTotal()));
            }
            response.getInventario().add(i);
        }
        return response;
    }
}
