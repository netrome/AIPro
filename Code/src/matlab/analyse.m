clear
%Get files, excluding . and ..
files = dir('../../results');
files = files(3:end);

fig1 = figure('name','PerformaceVsSteps');
hold on;
title('Performance VS Steps')

for file=files';
   data = csvread(strcat('../../results/',file.name));
   %Extract the interesting parts of the filename
   expression = 'A-(?<a>[^-_]*)_N-(?<n>\d*)_S-(?<w>\d*)x(?<h>\d*)_E-(?<e>\d*\.\d*).csv';
   meta = regexp(file.name,expression,'names');
   
   plot(data(:,1), data(:,3),'displayname',strcat(meta.a,' with ',meta.n,' agents on ',meta.w,'x',meta.h, ' map with easy: ',meta.e));
end

legend show;