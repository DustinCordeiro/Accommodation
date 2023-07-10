export interface Booking {
  id?: number,
  guestId: number,
  checkInDate?: string,
  checkOutDate?: string,
  hasCheckIn: boolean,
  hasCheckOut: boolean,
  hasVehicle: boolean,
  initialValue: number,
  finalValue: number
}
