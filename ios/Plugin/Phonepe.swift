import Foundation

@objc public class Phonepe: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
