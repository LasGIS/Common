import { get } from "./rest";

export function fetchEmployees() {
	return get(`/api/employees`);
}
