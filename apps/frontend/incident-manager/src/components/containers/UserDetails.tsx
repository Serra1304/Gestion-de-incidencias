/* "use client";

import Button from "../ui/Button";
import CheckboxField from "../ui/CheckBoxField";
import Container from "../ui/Container";
import FormField from "../ui/FormFiled";

import { useState } from "react";

export default function aUserDetails() {
    const [deleteUser, setDeleteUser] = useState(false);
    const [blockUser, setBlockUser] = useState(false);

    return (
        <Container title="Datos de registro">
            <form>
                <div className="flex flex-col gap-5">
                    <div className="flex">
                        <FormField label="Id" name="id" placeholder="Idendificador"/>
                    </div>
                    <div className="flex gap-10">
                        <FormField label="Nombre" name="name" placeholder="Nombre" />
                        <FormField label="Apellidos" name="lastName" placeholder="Apellidos"/>
                        <FormField label="Email" name="email" placeholder="Email"/>
                    </div>
                    <div className="flex gap-10">
                        <FormField label="Contraseña" name="password" type="password" placeholder="Contraseña"/>
                        <FormField label="Repita contraseña" name="passwordRepeat" type="password" placeholder="Repita contraseña" />
                    </div>
                    <div className="flex gap-10">
                        <FormField label="Grupo de trabajo" name="workGroup" placeholder="Grupo de trabajo" />
                        <FormField label="Rol" name="rol" placeholder="Rol" />
                    </div>
                    <Container title="Zona peligrosa" variant="danger" className="w-full">
                        <div className="flex w-full justify-between">
                        <div className="flex gap-10">
                            <CheckboxField label="Bloquear usuario" name="blockUser" variant="danger" checked={blockUser} onChange={setBlockUser} />
                            <CheckboxField label="Eliminar usuario" name="deleteUser" variant="danger" checked={deleteUser} onChange={setDeleteUser} />
                        </div>
                        <div>
                            <Button variant="danger" onClick={() => alert("Usuario eliminado")}>
                                Confirmar
                            </Button>
                        </div>
                        </div>
                    </Container>
                </div>
            </form>
        </Container>
    );
} */