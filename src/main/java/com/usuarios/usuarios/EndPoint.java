package com.usuarios.usuarios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import https.t4is_uv_mx.clientes.BusquedaPedidoIDRequest;
import https.t4is_uv_mx.clientes.BusquedaPedidoIDResponse;
import https.t4is_uv_mx.clientes.ListaPedidosResponse;
import https.t4is_uv_mx.clientes.RegistroRequest;
import https.t4is_uv_mx.clientes.RegistroResponse;

@Endpoint
public class EndPoint {

    Iterable<Pedidos> listaClientes;

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

        response.setRespuesta("Cliente Registrado");

        return response;
    }

    @PayloadRoot(localPart = "BusquedaPedidoIDRequest", namespace = "https://t4is.uv.mx/clientes")
    @ResponsePayload
    public BusquedaPedidoIDResponse buscarId(@RequestPayload BusquedaPedidoIDRequest peticion) {
        BusquedaPedidoIDResponse response = new BusquedaPedidoIDResponse();
        if (peticion.getId() == 0) {
            response.setStatus("400 Bad Request");
        } else {
            Pedidos pedido = iclientes.findById(peticion.getId()).get();
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
}
