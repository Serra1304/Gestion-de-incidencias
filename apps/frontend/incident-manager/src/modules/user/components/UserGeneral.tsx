
import { useContext, useEffect, useMemo, useState } from "react";
import FormField from "@/components/ui/FormFiled";
import CheckboxField from "@/components/ui/CheckBoxField";
import { VARIANTS } from "@/components/ui/theme/variants";
import { UserContext } from "@/app/(dashboard)/users/[userId]/layout";
import { UserFull } from "../type/userFull";
import MultiSelectField from "@/components/ui/MultiSelectField";
import { ReferenceDataContext } from "@/app/(dashboard)/users/layout";
import { mapGroupsToOptions } from "@/modules/common/mapper/selectOption.mapper";
import Button from "@/components/ui/Button";

export default function UserGeneral() {
    const { user, updateUser, setPassword } = useContext(UserContext);
    const { groups/*, roles*/ } = useContext(ReferenceDataContext);

    const [passwordInput, setPasswordInput] = useState("");
    const [confirmPasswordInput, setConfirmPasswordInput] = useState("");
    const [passwordTouched, setPasswordTouched] = useState(false);

    const groupOptions = useMemo(
        () => mapGroupsToOptions(groups),
        [groups]
    );

    if (!user) return null;

    const updateField = <K extends keyof UserFull>(
        field: K,
        value: UserFull[K]
    ) => {
        updateUser({ [field]: value } as Partial<UserFull>);
    };

    const handleGroupsChange = (groupIds: string[]) => {
        if (!groups) return;

        const selectedGroups = groups.filter(g => groupIds.includes(g.id));

        updateUser({ groups: selectedGroups });
    };

    useEffect(() => {
        if (!setPassword) return;

        if (!passwordTouched) {
            setPassword(null);
            return;
        }

        if (
            passwordInput.length > 0 &&
            passwordInput === confirmPasswordInput
        ) {
            setPassword(passwordInput);
        } else {
            setPassword(null);
        }
    }, [passwordInput, confirmPasswordInput, passwordTouched, setPassword]);


    return (
        <div className={`flex flex-col gap-5 w-full`}>
            <h4 className={`flex border-b border-emerald-700 ${VARIANTS.standard.text} mt-10`}>
                Datos de usuario
            </h4>

            {/* ID - solo lectura */}
            <FormField
                label="ID"
                name="id"
                value={user.id}
                disable={true}
                onChange={() => { }}
                className="w-80"
            />

            {/* Nombre */}
            <FormField
                label="Nombre"
                name="name"
                value={user.name}
                onChange={(val) => updateField("name", val)}
                className="w-80"

            />
            <div className="flex gap-15">
                <FormField
                    label="Primer Apellido"
                    name="firstSurname"
                    value={user.lastName}
                    onChange={(val) => updateField("lastName", val)}
                    className="w-80"
                />

                <FormField
                    label="Segundo Apellido"
                    name="secondtSurname"
                    value={user.secondLastName}
                    onChange={(val) => updateField("secondLastName", val)}
                    className="w-80"
                />
            </div>

            {/* Email */}
            <FormField
                label="Email"
                name="email"
                value={user.email}
                onChange={(val) => updateField("email", val)}
                className="w-80"
            />

            <div className="flex gap-15">
                <FormField
                    label="Contraseña"
                    name="password"
                    value={passwordInput}
                    onChange={(val) => {
                        setPasswordInput(val);
                        setPasswordTouched(true);
                    }}
                    className="w-80"
                />

                <FormField
                    label="Confirmar contraseña"
                    name="confirmPassword"
                    value={confirmPasswordInput}
                    onChange={(val) => {
                        setConfirmPasswordInput(val);
                        setPasswordTouched(true);
                    }}
                    className="w-80"
                />

                <Button>Cambiar contraseña</Button>
            </div>

            {/* Activo */}
            <CheckboxField
                label="Usuario activo"
                name="active"
                checked={user.active}
                onChange={(val) => updateField("active", val)}
            />

            <h4 className={`flex border-b border-emerald-700 ${VARIANTS.standard.text} mt-10`}>
                Acceso y permisos
            </h4>
            {/* Grupos */}
            <MultiSelectField
                label="Grupos"
                name="groups"
                value={user.groups.map(g => g.id)}
                onChange={handleGroupsChange}
                className="w-80"
                options={groupOptions}
            />

            <h4 className={`flex border-b border-emerald-700 ${VARIANTS.standard.text} mt-10`}>
                Direccion
            </h4>

            <div className="flex gap-15">
                <FormField
                    label="Direccion"
                    name="address"
                    value={user.address}
                    onChange={(val) => updateField("address", val)}
                    className="w-80"
                />

                <FormField
                    label="Numero"
                    name="Number"
                    value={user.addressNumber}
                    onChange={(val) => updateField("addressNumber", val)}
                    className="w-20"
                />
            </div>

            <div className="flex gap-15">
                <FormField
                    label="Poblacion"
                    name="town"
                    value={user.city}
                    onChange={(val) => updateField("city", val)}
                    className="w-80"

                />

                <FormField
                    label="Codigo postal"
                    name="zipCode"
                    value={user.postalCode}
                    onChange={(val) => updateField("postalCode", val)}
                    className="w-30"
                />
            </div>

            <FormField
                label="Ciudad"
                name="city"
                value={user.province}
                onChange={(val) => updateField("province", val)}
                className="w-80"
            />

            <h4 className={`flex border-b border-emerald-700 ${VARIANTS.standard.text} mt-10`}>
                Contacto
            </h4>

            <FormField
                label="Telefono"
                name="phone"
                value={user.phone}
                onChange={(val) => updateField("phone", val)}
                className="w-80"
            />

            <FormField
                label="Telefono de empresa"
                name="workPhone"
                value={user.phoneBusiness}
                onChange={(val) => updateField("phoneBusiness", val)}
                className="w-80"
            />

            <FormField
                label="Extension"
                name="extensionPhone"
                value={user.phoneExtension}
                onChange={(val) => updateField("phoneExtension", val)}
                className="w-30"
            />


            <h4 className={`flex border-b border-emerald-700 ${VARIANTS.standard.text} mt-10`}>
                Metadatos
            </h4>

            {/* Fechas */}
            <div className="grid grid-cols-2 gap-5">
                <FormField
                    label="Fecha de creación"
                    name="createdAt"
                    disable={true}
                //value={form.createdAt || ""}
                />
                <FormField
                    label="Última actualización"
                    name="updatedAt"
                    disable={true}
                //value={form.updatedAt || ""}
                />
            </div>
        </div>
    );
}