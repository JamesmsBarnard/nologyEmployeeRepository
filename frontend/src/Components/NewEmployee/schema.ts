import {  z } from "zod";

export const schema = z.object({
    firstName:   z.string().min(1, { error :"First name must not be blank"}),
    middleName:  z.string(),
    lastName:    z.string().min(1, { error :"Last name must not be blank"}),
    email:       z.string().min(1, { error :"Email must not be blank"}).includes("@", { error : "Email must include an adress"}),
    phoneNumber: z.string().regex(/^[0-9().]+$/, {error : "Phone number must only be made of numbers"}),
    address:     z.string().min(1, {error : "Address must not be blank"}),
    // typeId: z.coerce.number().min(1),
    // startDate: z.string(),
    // endDate: z.string(),
});

export type EmployeeFormData = z.infer<typeof schema>;