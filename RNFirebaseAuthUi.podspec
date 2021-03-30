
Pod::Spec.new do |s|
  s.name         = "RNFirebaseAuthUi"
  s.version      = "1.0.12"
  s.summary      = "RNFirebaseAuthUi"
  s.description  = "React native Firebase Authentication with UI"
  s.homepage     = "https://github.com/moonjava2005/react-native-firebase-auth-ui"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "moonjava@gmail.com" }
  s.platform     = :ios, "10.0"
  s.source       = { :git => "https://github.com/moonjava2005/react-native-firebase-auth-ui.git", :tag => "master" }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "FirebaseUI/Phone"
  s.dependency "FirebaseUI/Google"
  s.dependency "FirebaseUI/Auth"

end

