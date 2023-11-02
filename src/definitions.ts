export interface PhonepePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
