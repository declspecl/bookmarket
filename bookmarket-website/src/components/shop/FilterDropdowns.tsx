import { FilterDirection, FilterType } from "@/lib/shop/model";
import { Select, SelectContent, SelectItem, SelectTrigger } from "../ui/select";

interface FilterTypeDropdownProps {
    filterType: FilterType | null;
    setFilterType: (filterType: FilterType) => void;
}

export function FilterTypeDropdown({ filterType, setFilterType }: FilterTypeDropdownProps) {
    return (
        <Select value={filterType || FilterType.LIST_DATE} onValueChange={setFilterType}>
            <SelectTrigger>{filterType || "Select a filter..."}</SelectTrigger>

            <SelectContent>
                {Object.values(FilterType).map((filterTypeOption) => (
                    <SelectItem value={filterTypeOption}>{filterTypeOption}</SelectItem>
                ))}
            </SelectContent>
        </Select>
    );
}

interface FilterDirectionDropdownProps {
    filterDirection: FilterDirection | null;
    setFilterDirection: (filterDirection: FilterDirection) => void;
}

export function FilterDirectionDropdown({ filterDirection, setFilterDirection }: FilterDirectionDropdownProps) {
    return (
        <Select value={filterDirection || FilterDirection.DESC} onValueChange={setFilterDirection}>
            <SelectTrigger>{filterDirection || "Select a direction..."}</SelectTrigger>

            <SelectContent>
                {Object.values(FilterDirection).map((filterDirectionOption) => (
                    <SelectItem value={filterDirectionOption}>{filterDirectionOption}</SelectItem>
                ))}
            </SelectContent>
        </Select>
    );
}
